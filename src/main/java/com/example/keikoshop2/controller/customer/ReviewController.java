package com.example.keikoshop2.controller.customer;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.keikoshop2.model.Review;
import com.example.keikoshop2.service.IReviewService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final IReviewService reviewService;

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews() {
        return new ResponseEntity<>(reviewService.getAllReviews(), HttpStatus.FOUND);
    }

    @PostMapping("/add")
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