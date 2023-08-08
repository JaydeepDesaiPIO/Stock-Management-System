package com.spring.stockmanagement.controller;


import com.spring.stockmanagement.Enum.Role;
import com.spring.stockmanagement.entities.User;
import com.spring.stockmanagement.helper.Message;
import com.spring.stockmanagement.service.Interface.CompanyService;
import com.spring.stockmanagement.service.Interface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private CompanyService companyService;

    @ModelAttribute("admin")
    public User getUser(Principal principal) {
        String currentUserName = principal.getName();
        User CurrentUser = userService.findByName(currentUserName).get();
        return CurrentUser;
    }

    @GetMapping("/")
    public String userDashboard() {
        return "admin/admin_dashboard";
    }

    @GetMapping("/users")
    public String allUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin/user_list.html";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable int id, HttpSession session) {
        User user = userService.findById(id);
        if (user.getRole().equals(Role.STOCKHOLDER)) {
            session.setAttribute("message", new Message("Unable to delete user with role STOCKHOLDER", "alert-danger"));
            return "redirect:/admin/users";
        } else if (user.getRole().equals(Role.ADMIN)) {
            session.setAttribute("message", new Message("Unable to delete user with role ADMIN", "alert-danger"));
            return "redirect:/admin/users";
        } else {
            session.setAttribute("message", new Message("User deleted", "alert-success"));
            userService.deleteUserById(id);
        }
        return "redirect:/admin/users";
    }

    @GetMapping("/companies")
    public String listOfCompany(Model model) {
        model.addAttribute("companies", companyService.getAllCompany());
        return "admin/company_list";
    }

    @GetMapping("/company/delete/{id}")
    public String deleteCompany(@PathVariable int id) {
        companyService.deleteCompanyById(id);
        return "redirect:/admin/companies";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable int id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "admin/update_admin";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String updateBook(@PathVariable int id,
                             @ModelAttribute("user") User user,
                             BindingResult bindingResult,
                             Model model) {
        // save updated user
        userService.validateUserForUpdate(user, id, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute(user);
            return "admin/update_admin";
        }
        userService.updateUser(user, id);
        return "redirect:/admin/";
    }
}
