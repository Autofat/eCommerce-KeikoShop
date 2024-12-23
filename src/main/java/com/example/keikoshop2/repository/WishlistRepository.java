package com.example.keikoshop2.repository;

import com.example.keikoshop2.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WishlistRepository extends JpaRepository<Wishlist, Integer> {
    List<Wishlist> findByUserId(int userId);

    boolean existsByUserIdAndProductId(int userId, int productId);

    Wishlist findByUserIdAndProductId(int userId, int productId);
}