package com.spring.stockmanagement.controller;

import com.spring.stockmanagement.Enum.Role;
import com.spring.stockmanagement.entities.User;
import com.spring.stockmanagement.helper.EmailSenderService;
import com.spring.stockmanagement.helper.Message;
import com.spring.stockmanagement.repositories.CompanyRepository;
import com.spring.stockmanagement.repositories.UserRepository;
import com.spring.stockmanagement.service.Interface.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private EmailSenderService emailSenderService;

    @ModelAttribute("roles")
    public List<Role> allRoles() {
        return List.of(Role.values());
    }


    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("Title", "Home Page");
        return "home.html";
    }

    @GetMapping("/user/signup")
    public String userSignup(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "user_signup";
    }

    @PostMapping("/user/register")
    public String userRegister(@ModelAttribute("user") User user, BindingResult bindingResult, Model model, HttpSession session) {
        userService.isUserValid(user, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute(user);
            return "user_signup";
        }
        emailSenderService.sendSimpleEmail("trail8385@gmail.com", "Hi " + user.getName() + ", You have Successfully registered to SMS, thanks for joining", "Successful Registration");
        userService.addUser(user);
        model.addAttribute("user", new User());
        session.setAttribute("message", new Message("Successfully Registered", "alert-success"));
        return "redirect:/home/user/signup";
    }

    @GetMapping("/signin")
    public String login(Model model) {
        model.addAttribute("title", "Login Page");
        return "login.html";
    }

    @GetMapping("/loadForgotPassword")
    public String loadForgotPassword(){
        return "forget_password";
    }
    @GetMapping("/loadResetPassword/{id}")
    public String loadResetPassword(@PathVariable("id") int id,Model model){
        model.addAttribute("userId",id);
        return "reset_password";
    }

    @PostMapping("/forgotPassword")
    public String forgotPassword(@RequestParam("email") String email, HttpSession session){
        User existingUser=userRepository.getUserByEmail(email);
        if(existingUser!=null)
        {
            return "redirect:/home/loadResetPassword/"+existingUser.getId();
        }
        else{
            session.setAttribute("message", new Message("Invalid email address", "alert-danger"));
            return "redirect:/home/loadForgotPassword";
        }
    }

    @PostMapping("/changePassword")
    public String resetPassword(@RequestParam("id") int id,@RequestParam("password") String password,@RequestParam("cpassword") String cpassword, HttpSession session)
    {
        if(StringUtils.isBlank(password)) {
            session.setAttribute("message", new Message("Password cannot be blank", "alert-danger"));
            return "redirect:/home/loadResetPassword/"+id;
        }
        if(!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,15}$")){
            session.setAttribute("message", new Message("Password must contain atleast one number, one UpperCase letter, one LowerCase letter, one Special Character and password length must 8-15 character", "alert-danger"));
            return "redirect:/home/loadResetPassword/"+id;        }
        if(userService.resetPassword(id,password,cpassword))
        {
            session.setAttribute("message", new Message("Password changed successfully", "alert-success"));
            return "redirect:/home/loadForgotPassword";
        }
        else
        {
            session.setAttribute("message", new Message("Password and Confirm password does not match, Please re-enter the password", "alert-danger"));
            return "redirect:/home/loadResetPassword/"+id;
        }
    }
}
