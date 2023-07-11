package com.spring.stockmanagement.controller;

import com.spring.stockmanagement.entities.Company;
import com.spring.stockmanagement.entities.User;
import com.spring.stockmanagement.repositories.CompanyRepository;
import com.spring.stockmanagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @GetMapping("/")
    public String home(Model model)
    {
        model.addAttribute("Title","Home Page");
        return "home.html";
    }

    @GetMapping("/company/signup")
    public String companySignup(Model model)
    {
        User user=new User();
        Company company=new Company();
        model.addAttribute("user",user);
        model.addAttribute("company",company);
        model.addAttribute("title","Registration form");
        return "companySignup.html";
    }

    @PostMapping("/company/register")
    public String companyRegister(@ModelAttribute("user") User user,@ModelAttribute("company") Company company)
    {
        companyRepository.save(company);
        user.setCompany(company);
        userRepository.save(user);
        return "redirect:/home/company/signup";
    }

    @GetMapping("/user/signup")
    public String userSignup(Model model)
    {
        User user=new User();
        model.addAttribute("user",user);
        return "user_signup";
    }

    @PostMapping("/user/register")
    public String userRegister(@ModelAttribute("user") User user)
    {
        userRepository.save(user);
        return "redirect:/home/user/signup";
    }
}
