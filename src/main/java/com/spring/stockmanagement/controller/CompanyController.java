package com.spring.stockmanagement.controller;

import com.spring.stockmanagement.entities.Company;
import com.spring.stockmanagement.entities.Product;
import com.spring.stockmanagement.entities.User;
import com.spring.stockmanagement.repositories.UserRepository;
import com.spring.stockmanagement.service.Interface.CompanyService;
import com.spring.stockmanagement.service.Interface.ProductService;
import com.spring.stockmanagement.service.Interface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

    @Autowired
    private CompanyService companyService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;


    @GetMapping("/")
    private String companyDashboard(Model model, Principal principal)
    {
        String currentUserName=principal.getName();
        User user=userRepository.getUserByName(currentUserName);
        model.addAttribute("user",user);
        return "company/company_dashboard";
    }

    @GetMapping("/signup")
    public String companySignup(Model model)
    {
        Company company=new Company();
        model.addAttribute("company",company);
        model.addAttribute("title","Registration form");
        return "companySignup.html";
    }

    @PostMapping("/register")
    public String companyRegister(@ModelAttribute("company") Company company, BindingResult bindingResult, Model model,Principal principal)
    {
        companyService.isCompanyValid(company,bindingResult);
        if(bindingResult.hasErrors())
        {
            model.addAttribute("company",company);
            return "companySignup";
        }
        companyService.saveCompany(company);
        String currentUserName=principal.getName();
        User CurrentUser=userRepository.getUserByName(currentUserName);
        CurrentUser.setCompany(company);
        userRepository.save(CurrentUser);
        return "redirect:/company/signup";
    }

    @GetMapping("/products/add")
    public String addProduct(Model model)
    {
        model.addAttribute("product",new Product());
        return "company/add";
    }

    @PostMapping("/products")
    public String saveProduct(@ModelAttribute("product")Product product,Principal principal)
    {
        String currentUserName=principal.getName();
        User CurrentUser=userRepository.getUserByName(currentUserName);
        product.setCompany(CurrentUser.getCompany());
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
