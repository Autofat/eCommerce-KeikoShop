package com.example.keikoshop2.service;

import java.util.List;
import com.example.keikoshop2.model.Wishlist;

public interface  IWishlistService {
    Wishlist store(Wishlist wishlist);
    List<Wishlist> getAllWishlist();
    void deleteWishlist(int id);
    void updateWishlist(int id, Wishlist updateWishlist);
    List<Wishlist> getAllWishlists();
}
