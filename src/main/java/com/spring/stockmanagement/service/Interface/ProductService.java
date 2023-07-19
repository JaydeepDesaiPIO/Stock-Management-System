package com.spring.stockmanagement.service.Interface;

import com.spring.stockmanagement.entities.OrderItem;
import com.spring.stockmanagement.entities.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Product addProduct(Product product);
    List<Product> getAllProduct();

    Product getProductById(int id);

    Optional<Product> isProductExist(String productName);

    void checkProduct(Product product, OrderItem orderItem);
}
