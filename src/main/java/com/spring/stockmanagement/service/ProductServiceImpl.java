package com.spring.stockmanagement.service;

import com.spring.stockmanagement.entities.Company;
import com.spring.stockmanagement.entities.Product;
import com.spring.stockmanagement.entities.User;
import com.spring.stockmanagement.repositories.ProductRepository;
import com.spring.stockmanagement.repositories.UserRepository;
import com.spring.stockmanagement.service.Interface.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductByCompany(Principal principal) {
        String currentUserName=principal.getName();
        User CurrentUser=userRepository.findByName(currentUserName).get();
        Company company=CurrentUser.getCompany();
        return productRepository.findProductByCompany(company);
    }

    @Override
    public Product getProductById(int id) {
        return productRepository.findById(id).get();
    }
}
