package com.gloryjewel.eatgo.interfaces;

import com.gloryjewel.eatgo.application.ReviewService;
import com.gloryjewel.eatgo.domain.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReviewController.class)
class ReviewControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean(ReviewService.class)
    private ReviewService reviewService;

    @Test
    public void createValid() throws Exception {
        given(reviewService.addReview(any(),any(),any(),any())).will(invocation ->{
            return Review.builder()
                    .name("youngju")
                    .description("맛있다.")
                    .id(1L)
                    .restaurantId(1L)
                    .score(3L)
                    .build();

        });

        mvc.perform(post("/restaurants/1/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"youngju\",\"score\":3,\"description\":\"맛있다.\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/restaurants/1/reviews/1"))
                .andExpect(content().string("{}"));

        verify(reviewService).addReview(any(),any(),any(),any());
    }

    @Test
    public void createValidWithAuthorization() throws Exception {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEsIm5hbWUiOiJTb24ifQ.HaojS4IeT_BAXybldfzi0YfULlsCMG-V9i0T42z9sqs";

        given(reviewService.addReview(1L,"Son",3L,"맛있다.")).will(invocation ->{
            return Review.builder()
                    .name("Son")
                    .description("맛있다.")
                    .id(1L)
                    .restaurantId(1L)
                    .score(3L)
                    .build();

        });

        mvc.perform(post("/restaurants/1/reviews")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"score\":3,\"description\":\"맛있다.\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/restaurants/1/reviews/1"))
                .andExpect(content().string("{}"));

        verify(reviewService).addReview(1L,"Son",3L,"맛있다.");
    }


    @Test
    public void createInValid() throws Exception {

        given(reviewService.addReview(anyLong(),any(),anyLong(),any())).will(invocation ->{
            return Review.builder()
                    .name("")
                    .description("")
                    .id(1L)
                    .score(3L)
                    .build();

        });

        mvc.perform(post("/restaurants/1/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"\",\"score\":,\"description\":\"맛있다.\"}"))
                .andExpect(status().isBadRequest());

        verify(reviewService, never()).addReview(anyLong(),any(),anyLong(),any());
    }


}