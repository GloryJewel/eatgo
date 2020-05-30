package com.gloryjewel.eatgo.interfaces;

import com.gloryjewel.eatgo.application.RestaurantService;
import com.gloryjewel.eatgo.domain.MenuItem;
import com.gloryjewel.eatgo.domain.Restaurant;
import com.gloryjewel.eatgo.domain.RestaurantNotFoundException;
import com.gloryjewel.eatgo.domain.Review;
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
                                    .id(1L)
                                    .categoryId(1L)
                                    .menuItems(Arrays.asList(
                                            MenuItem.builder()
                                            .name("kimchi")
                                            .build()
                                    ))
                                    .build());

        given(restaurantService.getRestaurants()).willReturn(restaurants);

        mvc.perform(get("/restaurants"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"name\":\"bob zip\"")))
                .andExpect(content().string(containsString("\"name\":\"kimchi\"")));
    }

    @Test
    public void detailWithExisted() throws Exception {

        Restaurant restaurant = Restaurant.builder()
                                .id(1L)
                                .categoryId(1L)
                                .name("bob zip")
                                .address("in seoul")
                                .build();

        given(restaurantService.getRestaurant(1L)).willReturn(restaurant);

        mvc.perform(get("/restaurants/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"name\":\"bob zip\"")))
                .andExpect(content().string(containsString("\"id\":1")));

    }

    @Test
    public void detailWithNotExisted() throws Exception { ;

        given(restaurantService.getRestaurant(404L))
                .willThrow(new RestaurantNotFoundException(404L));

        mvc.perform(get("/restaurants/404"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("{}"));

    }

    @Test
    public void createWithValidData() throws Exception {

        given(restaurantService.addRestaurant(any())).will(invocation ->{
            Restaurant restaurant = invocation.getArgument(0);
            return Restaurant.builder()
                                .name(restaurant.getName())
                                .address(restaurant.getAddress())
                                .id(1234L)
                                .categoryId(1L)
                                .build();

        });

        mvc.perform(post("/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"bob zip\",\"address\":\"BeRung\",\"id\":1234,\"categoryId\":1}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/restaurants/1234"))
                .andExpect(content().string("{}"));

        verify(restaurantService).addRestaurant(any());

    }

    @Test
    public void createWithInvalidData() throws Exception {

        mvc.perform(post("/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"\",\"address\":\"\",\"categoryId\":1}"))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void updateWhthValidData() throws Exception {

        mvc.perform(patch("/restaurants/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"bar\",\"address\":\"BeRung\",\"categoryId\":1}"))
                .andExpect(status().isOk());

        verify(restaurantService).updateRestaurant(1L, "bar", "BeRung");
    }

    @Test
    public void updateWhthInvalidData() throws Exception {

        mvc.perform(patch("/restaurants/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"\",\"address\":\"\",\"categoryId\":1}"))
                .andExpect(status().isBadRequest());

    }
}