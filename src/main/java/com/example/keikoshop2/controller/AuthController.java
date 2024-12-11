package com.example.keikoshop2.controller;

import com.example.keikoshop2.model.User;
import com.example.keikoshop2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/signup")
    public String showSignupPage() {
        return "register";
    }

    @PostMapping("/signup")
    public String processSignup(@RequestParam String fullname,
                                @RequestParam String email,
                                @RequestParam String password,
                                Model model) {
        try {
            User user = new User();
            user.setUsername(fullname.split(" ")[0]);
            user.setFullName(fullname);
            user.setEmail(email);
            user.setPassword(password);
            user.setRole("ROLE_customer");

            userService.register(user);
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "register";
        }
    }

}