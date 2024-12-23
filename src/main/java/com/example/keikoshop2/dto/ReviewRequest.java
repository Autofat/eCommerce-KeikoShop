package com.example.keikoshop2.dto;

import com.example.keikoshop2.model.Review;
import java.util.List;

public class ReviewRequest {
  private List<Review> reviews;

  public List<Review> getReviews() {
    return reviews;
  }

  public void setReviews(List<Review> reviews) {
    this.reviews = reviews;
  }
}