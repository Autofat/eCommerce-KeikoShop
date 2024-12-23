package com.example.keikoshop2.service;

import com.example.keikoshop2.model.Wishlist;
import java.util.List;

public interface IWishlistService {
    void store(Wishlist wishlist);
    List<Wishlist> getWishlistsByUserId(int userId);
    void deleteWishlist(int id);
    void updateWishlist(int id, Wishlist updatedWishlist);
    boolean existsByUserIdAndProductId(int userId, int productId);
    Integer findWishlistIdByUserIdAndProductId(int userId, int productId);
}