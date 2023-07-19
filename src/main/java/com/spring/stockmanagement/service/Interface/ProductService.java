package com.spring.stockmanagement.service.Interface;

import com.spring.stockmanagement.entities.Product;

import java.security.Principal;
import java.util.List;

public interface ProductService {

    Product addProduct(Product product);
    List<Product> getAllProduct();

    List<Product> getProductByCompany(Principal principal);
    Product getProductById(int id);
}
