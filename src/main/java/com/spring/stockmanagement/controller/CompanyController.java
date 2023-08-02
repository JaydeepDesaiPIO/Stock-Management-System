package com.spring.stockmanagement.controller;

import com.spring.stockmanagement.entities.Company;
import com.spring.stockmanagement.entities.Product;
import com.spring.stockmanagement.entities.User;
import com.spring.stockmanagement.helper.Message;
import com.spring.stockmanagement.repositories.CompanyRepository;
import com.spring.stockmanagement.repositories.UserRepository;
import com.spring.stockmanagement.service.Interface.CompanyService;
import com.spring.stockmanagement.service.Interface.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserRepository userRepository;

    @ModelAttribute("currentCompany")
    public Company getCompany(Principal principal)
    {
        String username=principal.getName();
        User user=userRepository.findByName(username).get();
        Company company=user.getCompany();
        return company;
    }


    @GetMapping("/")
    public String companyDashboard(Model model, Principal principal)
    {
        String currentUserName=principal.getName();
        User CurrentUser=userRepository.findByName(currentUserName).get();
        model.addAttribute("user",CurrentUser);
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
    public String companyRegister(@ModelAttribute("company") Company company, BindingResult bindingResult, Model model, Principal principal, HttpSession session)
    {
        companyService.validateCompany(company,bindingResult);
        if(bindingResult.hasErrors())
        {
            model.addAttribute("company",company);
            return "companySignup";
        }
        companyService.saveCompany(company,principal);
        session.setAttribute("message", new Message("Registration Successful", "alert-success"));
        return "redirect:/company/signup";
    }

    @GetMapping("/products/add")
    public String addProduct(Model model)
    {
        Product product=new Product();
        model.addAttribute("product",product);
        return "company/add";
    }

    @PostMapping("/products")
    public String saveProduct(@ModelAttribute("product")Product product,Principal principal)
    {
        String currentUserName=principal.getName();
        User CurrentUser=userRepository.findByName(currentUserName).get();
        product.setCompany(CurrentUser.getCompany());
        productService.addProduct(product);
        return "redirect:/company/products";
    }

    @GetMapping("/update/{id}")
    public String editCompanyDetails(@PathVariable("id") int id, Model model){
        model.addAttribute("company",companyRepository.findById(id));
        return "company/update";
    }

    @GetMapping("/products")
    public String getAllProducts(Model model,Principal principal)
    {
        String currentUserName=principal.getName();
        User CurrentUser=userRepository.findByName(currentUserName).get();
        if(CurrentUser.getCompany()==null){
            return "company/please_register";
        }
        model.addAttribute("products",productService.getProductByCompany(principal));
        return "company/products";
    }
}
