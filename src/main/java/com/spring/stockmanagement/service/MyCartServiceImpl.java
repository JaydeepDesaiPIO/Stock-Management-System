package com.spring.stockmanagement.service;

import com.spring.stockmanagement.entities.MyCart;
import com.spring.stockmanagement.entities.User;
import com.spring.stockmanagement.repositories.MyCartRepository;
import com.spring.stockmanagement.service.Interface.MyCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyCartServiceImpl implements MyCartService {

    @Autowired
    private MyCartRepository myCartRepository;

    @Override
    public List<MyCart> findByUser(User user) {
        return myCartRepository.getCartByUser(user);
    }
}
