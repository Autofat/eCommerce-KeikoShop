package com.example.keikoshop2.repository;

import com.example.keikoshop2.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    // Find all reviews for a specific product
    List<Review> findByProductId(Integer productId);

    // Find all reviews by a specific user
    List<Review> findByUserId(Integer userId);

    // Calculate average rating for a product
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.productId = :productId")
    Double getAverageRatingByProductId(@Param("productId") Integer productId);
}