package com.example.keikoshop2.controller.customer;

import com.example.keikoshop2.model.Review;
import com.example.keikoshop2.service.IReviewService;
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