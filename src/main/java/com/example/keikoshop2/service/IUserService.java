package com.example.keikoshop2.service;

import com.example.keikoshop2.model.User;

public interface IUserService{
    User register(User user);
    void login(String username, String password);
    void logout(String username);
}