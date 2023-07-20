package com.spring.stockmanagement.controller;

import com.spring.stockmanagement.entities.*;
import com.spring.stockmanagement.repositories.*;
import com.spring.stockmanagement.service.Interface.MyCartService;
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
    public String addOrderItemToOrderList(@ModelAttribute("product") Product product, @ModelAttribute("orderItem") OrderItem orderItem, Principal principal, Model model) {
        productService.saveOrderItem(product, orderItem);
        if (productService.isProductExits(product.getProductName()).isPresent()) {
            Product product1 = productService.findProductByName(product.getProductName());

            MyCart myCart = new MyCart();
            myCart.setProduct(product1);
            String currentUserName = principal.getName();
            User CurrentUser = userRepository.findByName(currentUserName).get();
            myCart.setUser(CurrentUser);
            myCartRepository.save(myCart);
            model.addAttribute("mycart", myCartService.findByUser(CurrentUser));
        }
            return "user/my_cart";
    }

}
