package com.example.keikoshop2.controller.api;

import ch.qos.logback.core.model.Model;
import com.example.keikoshop2.model.User;
import com.example.keikoshop2.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
    public UserDetails login(@RequestParam String username, @RequestParam String password) {
        return userService.login(username, password);
    }

    @PostMapping("/logout")
    public ModelAndView logout(@RequestParam String username) {
        userService.logout(username);
        return new ModelAndView("redirect:/login");
    }

    @GetMapping("/findByEmail")
    public User findByEmail(@RequestParam String email) {
        return userService.findByEmail(email);
    }

    @GetMapping("/currentUser")
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userService.findByUsername(username);
    }


}
