package com.gloryjewel.eatgo.application;

import com.gloryjewel.eatgo.application.RestaurantService;
import com.gloryjewel.eatgo.domain.*;
import com.gloryjewel.eatgo.interfaces.RestaurantController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.groupingBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

class RestaurantServiceTests {

    private RestaurantService restaurantService;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private MenuItemRepository menuItemRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        restaurantService = new RestaurantService(
                restaurantRepository,
                menuItemRepository,
                reviewRepository);

        mockRestaurantRepository();
        mockMenuItemRepository();
        mockReviewRepository();
    }

    private void mockRestaurantRepository() {
        List<Restaurant> restaurants = new ArrayList<>();

        Restaurant restaurant = Restaurant.builder()
                                            .name("bob zip")
                                            .address("in seousl")
                                            .categoryId(1L)
                                            .id(1L)
                                            .build();
        restaurants.add(restaurant);

        given(restaurantRepository.findAllByAddressContainingAndCategoryId("서울", 1L)).willReturn(restaurants);
        given(restaurantRepository.findById(1L)).willReturn(Optional.of(restaurant));


    }

    private void mockMenuItemRepository(){

        List<MenuItem> menuItems = new ArrayList<>();
        MenuItem menuItem = MenuItem.builder().name("kimchi").build();
        menuItems.add(menuItem);

        given(menuItemRepository.findAllByRestaurantId(1L)).willReturn(menuItems);
    }

    private void mockReviewRepository(){

        List<Review> reviews = new ArrayList<>();
        Review review = Review.builder().name("youngju").build();
        reviews.add(review);

        given(reviewRepository.findAllByRestaurantId(1L)).willReturn(reviews);
    }

    @Test
    public void getRestaurants(){
        List<Restaurant> restaurants = restaurantService.getRestaurants("서울", 1L);

        assertEquals(restaurants.get(0).getId(), 1L);
        //assertEquals(restaurants.get(0).getMenuItems().get(0).getName(), "kimchi");

    }

    @Test
    public void getRestaurantWithExisted(){
        Restaurant restaurant = restaurantService.getRestaurant(1L);

        assertEquals(restaurant.getMenuItems().get(0).getName(), "kimchi");
        assertEquals(restaurant.getReviews().get(0).getName(), "youngju");
    }

    @Test
    public void getRestaurantWithNotExisted(){

        Assertions.assertThrows(RestaurantNotFoundException.class, () -> {
            Restaurant restaurant = restaurantService.getRestaurant(404L);
        });

    }

}