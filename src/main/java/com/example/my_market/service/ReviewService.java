package com.example.my_market.service;

import com.example.my_market.entity.Product;
import com.example.my_market.entity.Review;
import com.example.my_market.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<Review> findAllByProduct(Product product) {
        return reviewRepository.findAllByProduct(product);
    }

    public void saveReview(Review review) {
        reviewRepository.save(review);
    }
}
