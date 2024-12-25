package com.example.keikoshop2.controller.customer;

import com.example.keikoshop2.model.Review;
import com.example.keikoshop2.model.Transactions;
import com.example.keikoshop2.service.IReviewService;
import com.example.keikoshop2.service.IPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import com.example.keikoshop2.service.PaymentService;
import com.example.keikoshop2.service.UserService;
import com.example.keikoshop2.model.User;
import org.springframework.security.core.Authentication;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private final IReviewService reviewService;
    private final IPaymentService transactionService;
    private final PaymentService paymentService;
    @Autowired
    private final UserService userService;

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

    @GetMapping("/all")
    public String getAllTransactions(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        List<Transactions> transactions = paymentService.getTransactionsByUserId(user.getId());
        
        model.addAttribute("transactions", transactions);
        model.addAttribute("user", user);
        return "reviews";
    }

    @GetMapping("/unpaid")
    public String getUnpaidTransactions(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        List<Transactions> transactions = paymentService.getTransactionsByUserId(user.getId())
            .stream()
            .filter(transaction -> !transaction.IsPaid())
            .collect(Collectors.toList());
        
        model.addAttribute("transactions", transactions);
        model.addAttribute("user", user);
        return "reviews";
    }

    @GetMapping("/completed")
    public String getCompletedTransactions(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        List<Transactions> transactions = paymentService.getTransactionsByUserId(user.getId())
            .stream()
            .filter(transaction -> transaction.IsPaid() && transaction.isConfirmed())
            .collect(Collectors.toList());

        model.addAttribute("transactions", transactions);
        model.addAttribute("user", user);
        return "reviews";
    }

    @GetMapping("/cancelled")
    public String getCancelledTransactions(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        List<Transactions> transactions = paymentService.getTransactionsByUserId(user.getId())
            .stream()
            .filter(transaction -> transaction.isCancelled())
            .collect(Collectors.toList());

        model.addAttribute("transactions", transactions);
        model.addAttribute("user", user);
        return "reviews";
    }
}