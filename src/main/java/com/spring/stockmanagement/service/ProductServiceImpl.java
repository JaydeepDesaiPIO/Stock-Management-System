package com.spring.stockmanagement.service;

import com.spring.stockmanagement.entities.OrderItem;
import com.spring.stockmanagement.entities.Product;
import com.spring.stockmanagement.repositories.ProductRepository;
import com.spring.stockmanagement.service.Interface.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(int id) {
        return productRepository.findById(id).get();
    }

    @Override
    public Optional<Product> isProductExist(String productName) {
        return productRepository.findByProductName(productName);
    }

    @Override
    public void checkProduct(Product product, OrderItem orderItem) {
        if(isProductExist(product.getProductName()).isPresent()) {
            Product product1=productRepository.getProductByProductName(product.getProductName());
            orderItem.setProduct(product1);
        }
    }
}
