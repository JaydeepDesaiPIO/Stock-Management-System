package com.spring.stockmanagement.controller;

import com.spring.stockmanagement.entities.Product;
import com.spring.stockmanagement.service.Interface.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products/add")
    public String addProduct(Model model)
    {
        Product product=new Product();
        model.addAttribute("product",product);
        return "company/add";
    }

    @PostMapping("/products")
    public String saveProduct(@ModelAttribute("product")Product product)
    {
        productService.addProduct(product);
        return "redirect:/company/products";
    }

    @GetMapping("/products")
    public String getAllProducts(Model model)
    {
        model.addAttribute("products",productService.getAllProduct());
        return "company/products";
    }
}
