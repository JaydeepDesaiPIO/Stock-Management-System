package com.spring.stockmanagement.service.Interface;


import com.spring.stockmanagement.entities.MyCart;
import com.spring.stockmanagement.entities.Product;
import com.spring.stockmanagement.entities.User;

import java.security.Principal;
import java.util.List;

public interface MyCartService {

    List<MyCart> findByUser(User user);

    void addProduct(Product product, MyCart myCart, Principal principal);
}
