package com.gloryjewel.eatgo.application;

import com.gloryjewel.eatgo.domain.Review;
import com.gloryjewel.eatgo.domain.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class ReviewServiceTests {

    private ReviewService reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        reviewService = new ReviewService(reviewRepository);
    }

    @Test
    void list(){

        Review review = Review.builder()
                                .name("young")
                                .build();

        given(reviewRepository.findAll()).willReturn(Arrays.asList(review));

        List<Review> reviews = reviewService.getReviews();

        assertEquals(reviews.get(0).getName(), "young");
    }
}