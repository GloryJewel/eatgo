package com.gloryjewel.eatgo.application;

import com.gloryjewel.eatgo.domain.Review;
import com.gloryjewel.eatgo.domain.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ReviewService {

    private ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public Review addReview(Review review) {

        return reviewRepository.save(review);
    }

    public List<Review> getReviews() {

        return reviewRepository.findAll();
    }
}
