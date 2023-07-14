package com.spring.stockmanagement.service.Interface;

import com.spring.stockmanagement.entities.Product;

import java.util.List;

public interface ProductService {

    Product addProduct(Product product);
    List<Product> getAllProduct();

    Product getProductById(int id);
}
