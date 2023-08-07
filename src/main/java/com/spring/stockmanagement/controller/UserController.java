package com.spring.stockmanagement.controller;

import com.spring.stockmanagement.entities.*;
import com.spring.stockmanagement.helper.Message;
import com.spring.stockmanagement.repositories.*;
import com.spring.stockmanagement.service.Interface.MyCartService;
import com.spring.stockmanagement.service.Interface.ProductService;
import com.spring.stockmanagement.service.Interface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ProductService productService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private MyCartRepository myCartRepository;
    @Autowired
    private MyCartService myCartService;
    @Autowired
    private CompanyRepository companyRepository;

    @ModelAttribute("currentUser")
    public User getUser(Principal principal) {
        String currentUserName = principal.getName();
        User CurrentUser = userRepository.findByName(currentUserName).get();
        return CurrentUser;
    }

    @GetMapping("/")
    public String userDashboard() {
        return "user/user_dashboard";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable int id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "user/edit_user";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String updateBook(@PathVariable int id,
                             @ModelAttribute("user") User user,
                             BindingResult bindingResult,
                             Model model, HttpSession session) {
        // save updated user
        userService.validateUserForUpdate(user, id, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute(user);
            return "user/edit_user";
        }
        userService.updateUser(user, id);
        session.setAttribute("message", new Message("Updated successfully!!", "alert-success"));
        return "redirect:/user/";
    }

    @GetMapping("/products")
    public String getAllProducts(Model model, Principal principal) {
        String currentUserName = principal.getName();
        User CurrentUser = userRepository.findByName(currentUserName).get();
        if (CurrentUser.getCompany() == null) {
            return "user/message";
        }
        model.addAttribute("products", productService.getProductByCompany(principal));
        return "user/products";
    }

    @GetMapping("/selectCompany")
    public String selectCompany(Model model) {
        model.addAttribute("companies", companyRepository.findAll());
        return "user/select_company";
    }

    @GetMapping("/set/company/{id}")
    public String setCompany(@PathVariable("id") int id, Principal principal) {
        userService.setCompany(id, principal);
        return "redirect:/user/products";
    }

    @GetMapping("/order/{id}")
    public String orderProduct(@PathVariable("id") int id, Model model) {
        OrderItem orderItem = new OrderItem();
        model.addAttribute("orderItem", orderItem);
        model.addAttribute("product", productService.getProductById(id));
        return "user/order";
    }

    @PostMapping("/add/order")
    public String addOrderItemToOrderList(@ModelAttribute("product") Product product,
                                          @ModelAttribute("orderItem") OrderItem orderItem,
                                          BindingResult bindingResult, HttpSession session,
                                          Model model, Principal principal) {

        //find and set product to orderItem
        productService.saveOrderItem(product, orderItem, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("product", product);
            model.addAttribute("orderItem", orderItem);
            return "user/order";
        }

        //order obj
        Orders order = new Orders();
        //set order to orderItem
        orderItem.setOrders(order);
        //set orderItem list to order
        order.setOrderItems(List.of(orderItem));
        //get current user
        String currentUserName = principal.getName();
        User CurrentUser = userRepository.findByName(currentUserName).get();
        //set user to order
        order.setUser(CurrentUser);

        Product product1 = productService.getProductById(product.getProductId());
        Company company = product1.getCompany();
        company.getUser().add(CurrentUser);
        companyRepository.save(company);
        // order.setOrderDate();

        orderItemRepository.save(orderItem);
        orderRepository.save(order);

        session.setAttribute("message", new Message("Order placed successfully!!", "alert-success"));
        return "user/order";
    }

    @GetMapping("/mycart/{id}")
    public String addProductToCart(@PathVariable("id") int id, Model model) {

        Product product = productService.getProductById(id);
        MyCart myCart = new MyCart();
        model.addAttribute("product", product);
        model.addAttribute("myCart", myCart);
        return "user/addToCart";
    }

    @PostMapping("/addToCart")
    public String addToCart(@ModelAttribute("product") Product product, @ModelAttribute("myCart") MyCart myCart, BindingResult bindingResult, Principal principal, HttpSession session, Model model) {
        productService.addProductToCart(product, myCart, principal, session, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("myCart", myCart);
            return "user/addToCart";
        }
        session.setAttribute("message", new Message("Product added to cart", "alert-success"));
        return "redirect:/user/products";
    }

    @GetMapping("/mycart")
    public String myCart(Model model, Principal principal) {
        String currentUserName = principal.getName();
        User CurrentUser = userRepository.findByName(currentUserName).get();
        model.addAttribute("mycart", myCartService.findByUser(CurrentUser));
        return "user/my_cart";
    }

    @GetMapping("/buy")
    public String buyProductsFromCart(Principal principal) {
        String currentUserName = principal.getName();
        User CurrentUser = userRepository.findByName(currentUserName).get();
        Orders orders = new Orders();
        orders.setUser(CurrentUser);
        List<OrderItem> orderItemList = new ArrayList<>();
        for (MyCart mycart : myCartService.findByUser(CurrentUser)) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(mycart.getProduct().getProductName());
            orderItem.setCompany(mycart.getProduct().getCompany().getCompanyName());
            orderItem.setQuantity(mycart.getProductCount());
            orderItem.setPrice(mycart.getProduct().getProductPrice());
            orderItem.setTotalPrice(mycart.getProductCount() * mycart.getProduct().getProductPrice());
            orderItem.setOrders(orders);

            orderItemList.add(orderItem);
            orders.setOrderItems(orderItemList);
            orderRepository.save(orders);
            orderItemRepository.save(orderItem);

            Company company = companyRepository.findByCompanyName(mycart.getProduct().getCompany().getCompanyName()).get();
            company.getUser().add(CurrentUser);
            companyRepository.save(company);
            myCartRepository.deleteAll();
        }
        return "user/success";
    }

    @GetMapping("/remove/{id}")
    public String removeFromCart(@PathVariable("id") int id, Principal principal) {
        userService.removeFromCart(id, principal);
        return "redirect:/user/mycart";
    }
}
