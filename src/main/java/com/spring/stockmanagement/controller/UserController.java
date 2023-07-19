package com.spring.stockmanagement.controller;

import com.spring.stockmanagement.entities.OrderItem;
import com.spring.stockmanagement.entities.Orders;
import com.spring.stockmanagement.entities.Product;
import com.spring.stockmanagement.entities.User;
import com.spring.stockmanagement.repositories.OrderItemRepository;
import com.spring.stockmanagement.repositories.OrderRepository;
import com.spring.stockmanagement.repositories.ProductRepository;
import com.spring.stockmanagement.repositories.UserRepository;
import com.spring.stockmanagement.service.Interface.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/")
    private String userDashboard(Model model, Principal principal)
    {
        String currentUserName=principal.getName();
        User user=userRepository.getUserByName(currentUserName);
        model.addAttribute("user",user);
        return "user/user_dashboard";
    }

    @GetMapping("/products")
    public String getAllProducts(Model model)
    {
        model.addAttribute("products",productService.getAllProduct());
        return "user/products";
    }

    @GetMapping("/order/{id}")
    public String orderProduct(@PathVariable ("id") int id,Model model)
    {
        OrderItem orderItem=new OrderItem();
        model.addAttribute("orderItem",orderItem);
        Product product=productService.getProductById(id);
        model.addAttribute("product",product);
        return "user/order";
    }

    @PostMapping("/add/order")
    public String addOrderItemToOrderList(@ModelAttribute("orderItem") OrderItem orderItem,@ModelAttribute("product") Product product)
    {
        productService.checkProduct(product,orderItem);
        orderItemRepository.save(orderItem);
        return "redirect:/user/products";
    }
}
