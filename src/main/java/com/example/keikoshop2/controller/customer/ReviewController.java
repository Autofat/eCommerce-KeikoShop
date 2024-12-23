package com.example.keikoshop2.controller.customer;

import com.example.keikoshop2.dto.ReviewRequest;
import com.example.keikoshop2.model.Review;
import com.example.keikoshop2.service.IReviewService;
import com.example.keikoshop2.service.IPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final IReviewService reviewService;
    private final IPaymentService transactionService;

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews() {
        return new ResponseEntity<>(reviewService.getAllReviews(), HttpStatus.FOUND);
    }

    @PostMapping("/add")
    public String createReview(@ModelAttribute Review review, RedirectAttributes redirectAttributes) {
        try {
            // Periksa apakah pesanan sudah pernah direview
            boolean alreadyReviewed = reviewService.isOrderAlreadyReviewed(review.getProductId());

            if (alreadyReviewed) {
                // Jika sudah direview, kembalikan pesan error
                redirectAttributes.addFlashAttribute("errorMessage",
                        "Anda sudah menyelesaikan review untuk pesanan ini");
                return "redirect:/pesanan-saya";
            }

            // Jika belum direview, lanjutkan proses review
            reviewService.createReview(review);
            redirectAttributes.addFlashAttribute("successMessage", "Review berhasil ditambahkan");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/pesanan-saya";
    }

    @PostMapping("/add/{orderId}")
    @ResponseBody
    public ResponseEntity<?> createReviews(@RequestBody Review reviewRequest, @PathVariable String orderId) {
        try {
            // check if transaction already reviewed
            boolean alreadyReviewed = transactionService.isOrderAlreadyReviewed(orderId);

            if (alreadyReviewed) {
                return ResponseEntity.badRequest().body("Order already reviewed");
            }

            // Create all reviews
            for (Review review : reviewRequest.getReviews()) {
                reviewService.createReview(review);
            }

            // set transaction as isRating
            transactionService.setIsRating(orderId);

            return ResponseEntity.ok().body("Reviews added successfully");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
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