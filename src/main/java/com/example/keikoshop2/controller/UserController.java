package com.example.keikoshop2.controller;

import com.example.keikoshop2.model.User;
import com.example.keikoshop2.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.register(user);
    }

    @PostMapping("/login")
    public User login(@RequestParam String username, @RequestParam String password) {
        return userService.login(username, password);
    }

    @PostMapping("/logout")
    public String logout(@RequestParam String username) {
        userService.logout(username);
        return "User logged out successfully!";
    }
}
