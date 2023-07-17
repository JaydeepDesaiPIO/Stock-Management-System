package com.spring.stockmanagement.controller;

import com.spring.stockmanagement.Enum.Role;
import com.spring.stockmanagement.entities.Company;
import com.spring.stockmanagement.entities.User;
import com.spring.stockmanagement.helper.Message;
import com.spring.stockmanagement.repositories.CompanyRepository;
import com.spring.stockmanagement.repositories.UserRepository;
import com.spring.stockmanagement.service.Interface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.nio.file.Files;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private CompanyRepository companyRepository;

    @ModelAttribute("roles")
    public List<Role> allRoles()
    {
        return List.of(Role.values());
    }

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
    public String userRegister(@ModelAttribute("user") User user, BindingResult bindingResult, Model model, HttpSession session)
    {
        userService.isUserValid(user,bindingResult);

        if(bindingResult.hasErrors())
        {
            model.addAttribute(user);
            return "user_signup";
        }
        userRepository.save(user);
        model.addAttribute("user",new User());
        session.setAttribute("message", new Message("Successfully Registered", "alert-success"));
        return "redirect:/home/user/signup";
    }
}
