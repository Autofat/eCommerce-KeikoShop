package com.example.keikoshop2.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.keikoshop2.model.Wishlist;
import com.example.keikoshop2.repository.WishlistRepository;

@Service
public class WishlistService implements IWishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Override
    public Wishlist store(Wishlist wishlist) {
        return wishlistRepository.save(wishlist);
    }

    @Override
    public List<Wishlist> getAllWishlist() {
        return wishlistRepository.findAll();
    }

    @Override
    public void deleteWishlist(int id) {
        wishlistRepository.deleteById((long) id);
    }

    @Override
    public void updateWishlist(int id, Wishlist updateWishlist) {
        Wishlist existingWishlist = wishlistRepository.findById((long) id).orElseThrow(() -> new RuntimeException("Wishlist not found"));
        existingWishlist.setName(updateWishlist.getName());
        existingWishlist.setItems(updateWishlist.getItems());
        wishlistRepository.save(existingWishlist);
    }

    @Override
    public List<Wishlist> getAllWishlists() {
        return wishlistRepository.findAll();
    }
}