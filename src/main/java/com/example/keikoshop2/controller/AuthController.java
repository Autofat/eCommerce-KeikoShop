package com.example.keikoshop2.controller;

import com.example.keikoshop2.model.User;
import com.example.keikoshop2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;

    @Autowired
    private HttpSession session;

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        return "login";
    }

    @GetMapping("/signup")
    public String showSignupPage(Model model) {
        return "register";
    }


    @PostMapping("/login")
    public String processLogin(@RequestParam String email, @RequestParam String password, Model model, HttpSession session) {
        try {
            User user = userService.login(email, password);
            session.setAttribute("user", user);
            // If authentication is successful, redirect to the home page
            return "redirect:/home";
        } catch (RuntimeException e) {
            // If authentication fails, add an error message to the model
            model.addAttribute("errorMessage", "Invalid username or password!");
            return "login";
        }
    }

    @PostMapping("/signup")
    public String processSignup(@RequestParam String username, @RequestParam String fullname, @RequestParam String email, @RequestParam String password, Model model) {
        try {
            User user = new User();
            user.setUsername(username);
            user.setFullName(fullname);
            user.setEmail(email);
            user.setPassword(password);
            user.setRole("customer");
            userService.register(user);
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "register";
        }
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login?logout";
    }
}