package com.gloryjewel.eatgo.interfaces;

import com.gloryjewel.eatgo.application.RestaurantService;
import com.gloryjewel.eatgo.domain.MenuItem;
import com.gloryjewel.eatgo.domain.Restaurant;
import com.gloryjewel.eatgo.domain.RestaurantNotFoundException;
import com.gloryjewel.eatgo.domain.Review;
import com.gloryjewel.eatgo.interfaces.RestaurantController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RestaurantController.class)
class RestaurantControllerTests {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext ctx;

    @MockBean(RestaurantService.class)
    private RestaurantService restaurantService;

    @BeforeEach
    public void setUp(){

        this.mvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))  // 필터 추가
                .alwaysDo(print())
                .build();
    }

    @Test
    public void list() throws Exception {
        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(Restaurant.builder()
                                    .name("bob zip")
                                    .address("in seoul")
                                    .categoryId(1L)
                                    .id(1L)
                                    .menuItems(Arrays.asList(
                                            MenuItem.builder()
                                            .name("kimchi")
                                            .build()
                                    ))
                                    .build());

        given(restaurantService.getRestaurants("서울", 1L)).willReturn(restaurants);

        mvc.perform(get("/restaurants?region=서울&category=1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"name\":\"bob zip\"")))
                .andExpect(content().string(containsString("\"name\":\"kimchi\"")));
    }

    @Test
    public void detailWithExisted() throws Exception {

        Restaurant restaurant = Restaurant.builder()
                                .id(1L)
                                .name("bob zip")
                                .address("in seoul")
                                .build();

        restaurant.addMenuItem(MenuItem.builder().name("kimchi").build());
        Review review = new Review().builder()
                            .name("youngju")
                            .score(3L)
                            .description("맛있다.")
                            .restaurantId(restaurant.getId())
                            .id(1L)
                            .build();

        restaurant.setReviews(Arrays.asList(review));

        given(restaurantService.getRestaurant(1L)).willReturn(restaurant);

        mvc.perform(get("/restaurants/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"name\":\"bob zip\"")))
                .andExpect(content().string(containsString("\"id\":1")))
                .andExpect(content().string(containsString("\"kimchi\"")))
                .andExpect(content().string(containsString("\"description\":\"맛있다.\"")));

    }

    @Test
    public void detailWithNotExisted() throws Exception { ;

        given(restaurantService.getRestaurant(404L))
                .willThrow(new RestaurantNotFoundException(404L));

        mvc.perform(get("/restaurants/404"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("{}"));
    }
}