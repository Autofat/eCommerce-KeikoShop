package com.example.keikoshop2.service;

import com.example.keikoshop2.model.Cart;
import com.example.keikoshop2.model.Product;

import java.util.List;

public interface ICartService {
    List<Cart> getCartItemsByUserId(int userId);

    void addToCart(int userId, Product product, int quantity);

    void removeFromCart(int cartId);

    double getTotalPriceByUserId(int userId);

    Cart getCartItemById(int cartId);

    void updateQuantity(int cartId, int quantity);
}