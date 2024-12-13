package com.example.keikoshop2.service;

import com.example.keikoshop2.exception.ReviewNotFoundException;
import com.example.keikoshop2.model.Review;
import com.example.keikoshop2.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class ReviewService implements IReviewService {
    private final ReviewRepository reviewRepository;

    //cek apakah sudah pernah review
    public boolean isOrderAlreadyReviewed(int productId) {
        // Periksa apakah review untuk order ini sudah ada
        return reviewRepository.existsByproductId(productId);
    }

    @Override
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    @Override
    public Review createReview(Review review) {
        // Otomatis set review date saat create
        review.setReviewDate(new Date());

        // Rating (1-5)
        if (review.getRating() < 1 || review.getRating() > 5) {
            throw new RuntimeException("Rating must be between 1 and 5");
        }

        // Comment boleh kosong
        return reviewRepository.save(review);
    }

    @Override
    public Review getReviewById(int id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException("Review Not Found With Id: " + id));
    }

    @Override
    public List<Review> getReviewsByProductId(int productId) {
        List<Review> reviews = reviewRepository.findByProductId(productId);
        if (reviews.isEmpty()) {
            throw new ReviewNotFoundException("No Reviews Found For Product Id: " + productId);
        }
        return reviews;
    }

    @Override
    public List<Review> getReviewsByUserId(int userId) {
        List<Review> reviews = reviewRepository.findByUserId(userId);
        if (reviews.isEmpty()) {
            throw new ReviewNotFoundException("No Reviews Found For User Id: " + userId);
        }
        return reviews;
    }

    @Override
    public Double getAverageRatingForProduct(int productId) {
        Double averageRating = reviewRepository.getAverageRatingByProductId(productId);
        if (averageRating == null) {
            throw new ReviewNotFoundException("No Ratings Found For Product Id: " + productId);
        }
        return averageRating;
    }
}