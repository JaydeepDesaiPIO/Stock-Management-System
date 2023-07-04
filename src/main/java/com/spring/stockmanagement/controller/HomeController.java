package com.spring.stockmanagement.controller;

import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {


    @GetMapping("/")
    public String home(Model model)
    {
        model.addAttribute("Title","Home Page");
        return "home.html";
    }
}
