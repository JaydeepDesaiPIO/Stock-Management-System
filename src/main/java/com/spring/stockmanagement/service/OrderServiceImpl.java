package com.spring.stockmanagement.service;

import com.spring.stockmanagement.entities.*;
import com.spring.stockmanagement.repositories.*;
import com.spring.stockmanagement.service.Interface.MyCartService;
import com.spring.stockmanagement.service.Interface.OrderService;
import com.spring.stockmanagement.service.Interface.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MyCartRepository myCartRepository;

    @Autowired
    private MyCartService myCartService;

    @Override
    public void saveOrder(Product product, OrderItem orderItem, Principal principal) {
        Orders order = new Orders();
        orderItem.setOrders(order);                     //set order to orderItem
        List<OrderItem> orderItemList=new ArrayList<>(List.of(orderItem));
        order.setOrderItems(orderItemList);               //set orderItem list to order
        String currentUserName = principal.getName();
        User CurrentUser = userRepository.findByName(currentUserName).get();
        order.setUser(CurrentUser);              //set user to order
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate= formatter.format(date);
        order.setOrderDate(strDate);          //set order date and time

        orderRepository.save(order);
        orderItemRepository.save(orderItem);

//        Product product1 = productService.getProductById(product.getProductId());
//        Company company = product1.getCompany();
//        company.getUser().add(CurrentUser);
//        companyRepository.save(company);
    }

    @Override
    public void saveAllOrder(User user) {
        Orders orders = new Orders();
        orders.setUser(user);
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate= formatter.format(date);
        orders.setOrderDate(strDate);
        List<OrderItem> orderItemList = new ArrayList<>();
        for (MyCart mycart : myCartService.findByUser(user)) {
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
            company.getUser().add(user);
            companyRepository.save(company);
            myCartRepository.deleteAll();
        }
    }
}
