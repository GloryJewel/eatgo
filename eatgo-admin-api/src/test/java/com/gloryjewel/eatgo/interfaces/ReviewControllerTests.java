package com.gloryjewel.eatgo.interfaces;

import com.gloryjewel.eatgo.application.ReviewService;
import com.gloryjewel.eatgo.domain.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReviewController.class)
class ReviewControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean(ReviewService.class)
    private ReviewService reviewService;

    @Test
    public void list() throws Exception {

        Review review = Review.builder()
                .name("young")
                .build();

        given(reviewService.getReviews()).willReturn(Arrays.asList(review));

        mvc.perform(get("/reviews"))
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("\"name\":\"young\"")));
    }
}