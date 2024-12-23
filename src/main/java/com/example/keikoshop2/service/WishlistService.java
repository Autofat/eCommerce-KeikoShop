package com.example.keikoshop2.service;

import com.example.keikoshop2.model.Wishlist;
import com.example.keikoshop2.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishlistService implements IWishlistService {

    private final WishlistRepository wishlistRepository;

    @Override
    public void store(Wishlist wishlist) {
        wishlistRepository.save(wishlist);
    }

    @Override
    public List<Wishlist> getWishlistsByUserId(int userId) {
        return wishlistRepository.findByUserId(userId);
    }

    @Override
    public void deleteWishlist(int id) {
        wishlistRepository.deleteById(id);
    }

    @Override
    public void updateWishlist(int id, Wishlist updatedWishlist) {
        Wishlist wishlist = wishlistRepository.findById(id).orElseThrow(() -> new RuntimeException("Wishlist not found"));
        wishlist.setUserId(updatedWishlist.getUserId());
        wishlist.setProductId(updatedWishlist.getProductId());
        wishlistRepository.save(wishlist);
    }

    @Override
    public boolean existsByUserIdAndProductId(int userId, int productId) {
        return wishlistRepository.existsByUserIdAndProductId(userId, productId);
    }

    @Override
    public Integer findWishlistIdByUserIdAndProductId(int userId, int productId) {
        Wishlist wishlist = wishlistRepository.findByUserIdAndProductId(userId, productId);
        return wishlist != null ? wishlist.getId() : null;
    }
}