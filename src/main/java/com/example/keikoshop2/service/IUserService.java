package com.example.keikoshop2.service;

import com.example.keikoshop2.model.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface IUserService {
    User register(User user);

    User storeUser(User user);

    User updateUser(User user, int id);

    void deleteUser(int id);

    UserDetails login(String username, String password);

    void logout(String username);

    User findByEmail(String email);

    User findByUsername(String username);

    List<User> getAllUsers();

    User getUserById(int id);
}