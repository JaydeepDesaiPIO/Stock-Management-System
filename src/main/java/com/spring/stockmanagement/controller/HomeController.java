package com.spring.stockmanagement.controller;

import com.spring.stockmanagement.Enum.Role;
import com.spring.stockmanagement.entities.Company;
import com.spring.stockmanagement.entities.User;
import com.spring.stockmanagement.helper.EmailSenderService;
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

    @Autowired
    private EmailSenderService emailSenderService;

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
        emailSenderService.sendSimpleEmail("trail8385@gmail.com","Hi "+user.getName()+", You have Successfully registered to SMS, thanks for joining","Successful Registration");
        userService.addUser(user);
        model.addAttribute("user",new User());
        session.setAttribute("message", new Message("Successfully Registered", "alert-success"));
        return "redirect:/home/user/signup";
    }

    @GetMapping("/signin")
    public String login(Model model)
    {
        model.addAttribute("title","Login Page");
        return "login.html";
    }
}
