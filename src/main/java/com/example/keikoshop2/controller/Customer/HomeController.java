package com.example.keikoshop2.controller.Customer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String showHomePage(Model model) {
        model.addAttribute("message", "Welcome to KeikoShop!");
        return "Customer/home";
    }
}