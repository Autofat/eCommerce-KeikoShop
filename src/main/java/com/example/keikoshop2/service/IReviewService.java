package com.example.keikoshop2.service;

import com.example.keikoshop2.model.Review;
import java.util.List;

public interface IReviewService {
    Review createReview(Review review);
    List<Review> getAllReviews();
    Review getReviewById(int id);
    List<Review> getReviewsByProductId(int productId);
    List<Review> getReviewsByUserId(int userId);
    Double getAverageRatingForProduct(int productId);
}

