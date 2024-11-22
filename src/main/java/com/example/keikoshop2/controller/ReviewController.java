package com.example.keikoshop2.controller;

import com.example.keikoshop2.model.Review;
import com.example.keikoshop2.service.IReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final IReviewService reviewService;

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews() {
        return new ResponseEntity<>(reviewService.getAllReviews(), HttpStatus.FOUND);
    }

    @PostMapping
    public Review createReview(@RequestBody Review review) {
        return reviewService.createReview(review);
    }

    @GetMapping("/review/{id}")
    public Review getReviewById(@PathVariable int id) {
        return reviewService.getReviewById(id);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Review>> getReviewsByProductId(@PathVariable int productId) {
        return new ResponseEntity<>(reviewService.getReviewsByProductId(productId), HttpStatus.FOUND);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Review>> getReviewsByUserId(@PathVariable int userId) {
        return new ResponseEntity<>(reviewService.getReviewsByUserId(userId), HttpStatus.FOUND);
    }

    @GetMapping("/product/{productId}/rating/average")
    public ResponseEntity<Double> getAverageRatingForProduct(@PathVariable int productId) {
        return new ResponseEntity<>(reviewService.getAverageRatingForProduct(productId), HttpStatus.OK);
    }
}