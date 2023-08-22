package com.spring.stockmanagement.controller;

import com.spring.stockmanagement.entities.*;
import com.spring.stockmanagement.helper.Message;
import com.spring.stockmanagement.repositories.*;
import com.spring.stockmanagement.service.ExportPdf;
import com.spring.stockmanagement.service.Interface.MyCartService;
import com.spring.stockmanagement.service.Interface.OrderService;
import com.spring.stockmanagement.service.Interface.ProductService;
import com.spring.stockmanagement.service.Interface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;
import java.io.IOException;
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
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private MyCartService myCartService;
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private OrderService orderService;

    @ModelAttribute("currentUser")
    public User getUser(Principal principal) {
        String currentUserName = principal.getName();
        User currentUser = userRepository.findByName(currentUserName).get();
        return currentUser;
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
    public String updateUser(@PathVariable int id,
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
        orderService.saveOrder(product,orderItem,principal);
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
        productService.addProductToCart(product, myCart, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("myCart", myCart);
            return "user/addToCart";
        }
        myCartService.addProduct(product, myCart, principal);
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
    public String buyProductsFromCart(Principal principal,HttpSession session) {
        User user = getUser(principal);
        if (myCartService.findByUser(user).isEmpty()){
            session.setAttribute("message", new Message("Cart is empty", "alert-danger"));
            return "redirect:/user/mycart";
        }
        orderService.saveAllOrder(user);
        return "redirect:/user/orders";
    }

    @GetMapping("/remove/{id}")
    public String removeFromCart(@PathVariable("id") int id, Principal principal) {
        userService.removeFromCart(id, principal);
        return "redirect:/user/mycart";
    }

    @GetMapping("/orders")
    public String allOrders(Model model,Principal principal)
    {
        User user=getUser(principal);
        List<Orders> ordersList=orderRepository.getOrdersByUserId(user.getId());
        List<OrderItem> orderItemList=new ArrayList<>();
        for(Orders orders: ordersList)
        {
            orderItemList.addAll(orders.getOrderItems());
        }
        model.addAttribute("orderItem", orderItemList);
        return "user/order_history";
    }

    @GetMapping("/order/pdf")
    public ResponseEntity<InputStreamResource> userOrderPdf(Principal principal)  throws IOException {
        User user=getUser(principal);
        List<Orders> ordersList=orderRepository.getOrdersByUserId(user.getId());
        List<OrderItem> orderItemList=new ArrayList<>();
        for(Orders orders: ordersList)
        {
            orderItemList.addAll(orders.getOrderItems());
        }
        ByteArrayInputStream bis = ExportPdf.exportOrderPdf(orderItemList);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=OrderPDF.pdf");

        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
}
