package com.gloryjewel.eatgo.application;

import com.gloryjewel.eatgo.application.ReviewService;
import com.gloryjewel.eatgo.domain.Review;
import com.gloryjewel.eatgo.domain.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
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
    void addReview() {

        Review resource = Review.builder()
                                .name("영주")
                                .restaurantId(1L)
                                .score(3L)
                                .description("맛있다.")
                                .build();

         reviewService.addReview(resource.getRestaurantId(),
                                    resource.getName(),
                                    resource.getScore(),
                                    resource.getDescription());

         verify(reviewRepository).save(any());
    }
}