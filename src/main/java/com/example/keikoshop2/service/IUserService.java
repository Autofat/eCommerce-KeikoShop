package com.example.keikoshop2.service;

import com.example.keikoshop2.model.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface IUserService{
    User register(User user);
    UserDetails login(String username, String password);
    void logout(String username);
}