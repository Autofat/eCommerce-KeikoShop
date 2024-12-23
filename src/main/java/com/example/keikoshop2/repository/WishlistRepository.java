package com.example.keikoshop2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.keikoshop2.model.Wishlist;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Integer> {

    // // Retrieve all wishlists based on userId
    // List<Wishlist> findByUserId(@Param("userId") int userId);

    // // Retrieve wishlists based on item name (example search feature)
    // List<Wishlist> findByNameContainingIgnoreCase(@Param("name") String name);

    // // Delete all wishlist items based on userId
    // void deleteByUserId(@Param("userId") int userId);
}