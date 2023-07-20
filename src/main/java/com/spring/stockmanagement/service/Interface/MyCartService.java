package com.spring.stockmanagement.service.Interface;


import com.spring.stockmanagement.entities.MyCart;
import com.spring.stockmanagement.entities.User;

import java.util.List;

public interface MyCartService {

    List<MyCart> findByUser(User user);
}
