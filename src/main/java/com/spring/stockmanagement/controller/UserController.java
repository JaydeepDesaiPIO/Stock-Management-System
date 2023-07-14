package com.spring.stockmanagement.controller;

import com.spring.stockmanagement.entities.OrderItem;
import com.spring.stockmanagement.entities.Orders;
import com.spring.stockmanagement.entities.Product;
import com.spring.stockmanagement.repositories.OrderItemRepository;
import com.spring.stockmanagement.repositories.OrderRepository;
import com.spring.stockmanagement.repositories.ProductRepository;
import com.spring.stockmanagement.service.Interface.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/products")
    public String getAllProducts(Model model)
    {
        model.addAttribute("products",productService.getAllProduct());
        return "user/products";
    }

    @GetMapping("/order/{id}")
    public String orderProduct(@PathVariable ("id") int id,Model model)
    {
        Orders orders=new Orders();
        OrderItem orderItem=new OrderItem();
        model.addAttribute("order",orders);
        model.addAttribute("orderItem",orderItem);
        model.addAttribute("product",productService.getProductById(id));
        return "user/order";
    }

    @PostMapping("/add/order")
    public String addOrderItemToOrderList(@ModelAttribute("product")Product product
            ,@ModelAttribute("order") Orders orders
            ,@ModelAttribute("orderItem") OrderItem orderItem)
    {
        orderItem.setProduct(product);
        orderItem.setOrders(orders);
        orderItemRepository.save(orderItem);
        Orders orders1=orderItem.getOrders();
        List<OrderItem> items=new ArrayList<>();
        items.add(orderItem);
        orders1.getOrderItems().addAll(items);
        orderRepository.save(orders1);
        return "redirect:/user/order/{id}";
    }
}
