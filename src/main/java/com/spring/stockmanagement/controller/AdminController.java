package com.spring.stockmanagement.controller;


import com.spring.stockmanagement.entities.User;
import com.spring.stockmanagement.service.Interface.CompanyService;
import com.spring.stockmanagement.service.Interface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private CompanyService companyService;

    @GetMapping("/users")
    public String allUsers(Model model)
    {
        model.addAttribute("users",userService.getAllUsers());
        return "admin/user_list.html";
    }

    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable int id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "admin/edit_user";
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.POST)
    public String updateBook(@PathVariable int id,
                             @ModelAttribute("user") User user,
                             Model model)
    {
        // save updated book
        userService.update(user,id);
        return "redirect:/admin/users";
    }

    @GetMapping("/companies")
    public String listOfCompany(Model model)
    {
        model.addAttribute("companies",companyService.getAllCompany());
        return "admin/company_list";
    }
}
