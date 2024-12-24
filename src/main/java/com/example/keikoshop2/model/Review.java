package com.example.keikoshop2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer userId;
    private Integer productId;
    private Integer rating;
    private String comment;
    private Date reviewDate;

    @Transient
    private List<Review> reviews;
    private String username;

    // getter, setter
    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    // No-args constructor
    public Review() {
    }

    // Constructor with parameters
    public Review(Integer userId, Integer productId, Integer rating, String comment, Date reviewDate) {
        this.userId = userId;
        this.productId = productId;
        this.rating = rating;
        this.comment = comment;
        this.reviewDate = reviewDate;
    }

    public Review(Integer userId, Integer productId, Integer rating, Date reviewDate) {
        this.userId = userId;
        this.productId = productId;
        this.rating = rating;
        this.reviewDate = reviewDate;
    }


    // Getters
    public Integer getId() {
        return id;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getProductId() {
        return productId;
    }

    public Integer getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    // Setters
    public void setId(Integer id) {
        this.id = id;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Integer> getStars() {
        return IntStream.range(0, rating).boxed().collect(Collectors.toList());
    }
}