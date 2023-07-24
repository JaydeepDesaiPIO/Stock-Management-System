package com.spring.stockmanagement.controller;

import com.spring.stockmanagement.entities.*;
import com.spring.stockmanagement.repositories.*;
import com.spring.stockmanagement.service.Interface.MyCartService;
import com.spring.stockmanagement.service.Interface.ProductService;
import com.spring.stockmanagement.service.Interface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
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

    @GetMapping("/")
    public String userDashboard(Model model, Principal principal) {
        String currentUserName = principal.getName();
        User CurrentUser = userRepository.findByName(currentUserName).get();
        model.addAttribute("user", CurrentUser);
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
                             Model model)
    {
        // save updated user
        userService.update(user,id,bindingResult);
        if(bindingResult.hasErrors())
        {
            model.addAttribute(user);
            return "user/edit_user";
        }

        return "redirect:/user/";
    }

    @GetMapping("/products")
    public String getAllProducts(Model model) {
        model.addAttribute("products", productService.getAllProduct());
        return "user/products";
    }

    @GetMapping("/order/{id}")
    public String orderProduct(@PathVariable("id") int id, Model model) {
        OrderItem orderItem = new OrderItem();
        model.addAttribute("orderItem", orderItem);
        model.addAttribute("product", productService.getProductById(id));
        return "user/order";
    }

    @PostMapping("/add/order")
    public String addOrderItemToOrderList(@ModelAttribute("product") Product product, @ModelAttribute("orderItem") OrderItem orderItem, Model model, Principal principal) {

        //find and set product to orderItem
        productService.saveOrderItem(product, orderItem);

        //order obj
        Orders order=new Orders();
        //set order to orderItem
        orderItem.setOrders(order);
        //set orderItem list to order
        order.setOrderItems(List.of(orderItem));
        //get current user
        String currentUserName = principal.getName();
        User CurrentUser = userRepository.findByName(currentUserName).get();
        //set user to order
        order.setUser(CurrentUser);

       // order.setOrderDate();

        orderRepository.save(order);
        orderItemRepository.save(orderItem);

        return "user/my_cart";
    }

    @GetMapping("/mycart/{id}")
    public String addProductToCart(@PathVariable("id") int id, Model model, Principal principal) {

        Product product=productService.getProductById(id);
        MyCart myCart = new MyCart();
        myCart.setProduct(product);
        String currentUserName = principal.getName();
        User CurrentUser = userRepository.findByName(currentUserName).get();
        myCart.setUser(CurrentUser);
        myCartRepository.save(myCart);
        model.addAttribute("mycart", myCartService.findByUser(CurrentUser));
        return "user/my_cart";
    }

    @GetMapping("/mycart")
    public String myCart(Model model,Principal principal)
    {
        String currentUserName = principal.getName();
        User CurrentUser = userRepository.findByName(currentUserName).get();
        model.addAttribute("mycart", myCartService.findByUser(CurrentUser));
        return "user/my_cart";
    }

}
