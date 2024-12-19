package com.example.keikoshop2.service;

import com.example.keikoshop2.model.Wishlist;
import com.example.keikoshop2.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishlistService implements IWishlistService {

    private final WishlistRepository wishlistRepository;

    @Override
    public Wishlist store(Wishlist wishlist) {
        return wishlistRepository.save(wishlist);
    }

    @Override
    public List<Wishlist> getAllWishlists() {
        return wishlistRepository.findAll();
    }

    @Override
    public void deleteWishlist(Long id) {
        wishlistRepository.deleteById(id);
    }

    @Override
    public void updateWishlist(Long id, Wishlist updatedWishlist) {
        Wishlist wishlist = WishlistRepository.findAllById(id)
        wishlist.setName(updatedWishlist.getName());
        wishlist.setDescription(updatedWishlist.getDescription());
        wishlist.setPrice(updatedWishlist.getPrice());
        wishlistRepository.save(wishlist);
    }
}

