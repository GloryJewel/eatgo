package com.gloryjewel.eatgo.application;

import com.gloryjewel.eatgo.domain.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.groupingBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

class RestaurantServiceTests {

    RestaurantService restaurantService;

    @Mock
    RestaurantRepository restaurantRepository;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        restaurantService = new RestaurantService(
                restaurantRepository);

        mockRestaurantRepository();
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

        given(restaurantRepository.findAll()).willReturn(restaurants);
        given(restaurantRepository.findById(1L)).willReturn(Optional.of(restaurant));


    }

    @Test
    public void getRestaurants(){
        List<Restaurant> restaurants = restaurantService.getRestaurants();

        assertEquals(restaurants.get(0).getId(), 1L);
        //assertEquals(restaurants.get(0).getMenuItems().get(0).getName(), "kimchi");

    }

    @Test
    public void getRestaurantWithExisted(){
        Restaurant restaurant = restaurantService.getRestaurant(1L);

        assertEquals(restaurant.getId(), 1L);
    }

    @Test
    public void getRestaurantWithNotExisted(){

        Assertions.assertThrows(RestaurantNotFoundException.class, () -> {
            Restaurant restaurant = restaurantService.getRestaurant(404L);
        });

    }

    @Test
    public void addRestaurant(){
        Restaurant restaurant = Restaurant.builder()
                                            .name("bob zip")
                                            .address("BeRyong")
                                            .categoryId(1L)
                                            .build();

        given(restaurantRepository.save(any())).will(invocation -> {
            Restaurant restaurantReturn = invocation.getArgument(0);
            restaurantReturn.setId(1234L);
            return  restaurantReturn;
        });

        Restaurant restaurantReturn = restaurantService.addRestaurant(restaurant);

        assertEquals(restaurantReturn.getId(), 1234L);
    }

    @Test
    public void updateRestaurant(){

        Restaurant restaurant = Restaurant.builder()
                                            .name("bob zip")
                                            .address("BeRyong")
                                            .id(1L)
                                            .categoryId(1L)
                                            .build();

        given(restaurantRepository.findById(1L))
                .willReturn(Optional.of(restaurant));

        restaurantService.updateRestaurant(1L, "Hope zip", "Seoul");

        assertEquals(restaurant.getName(),  "Hope zip");
        assertEquals(restaurant.getAddress(), "Seoul");
    }

    @Test
    public void otionalTest(){

        System.out.println(Optional.ofNullable(null).orElse(""));
    }

}