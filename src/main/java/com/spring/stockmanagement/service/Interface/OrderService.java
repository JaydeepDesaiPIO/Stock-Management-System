package com.spring.stockmanagement.service.Interface;

import com.spring.stockmanagement.entities.OrderItem;
import com.spring.stockmanagement.entities.Product;
import com.spring.stockmanagement.entities.User;

import java.security.Principal;

public interface OrderService {
    void saveOrder(Product product, OrderItem orderItem, Principal principal);

    void saveAllOrder(User user);

    void setOrderStatus();
}
