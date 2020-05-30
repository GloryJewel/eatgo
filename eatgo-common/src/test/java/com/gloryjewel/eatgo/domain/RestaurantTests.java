package com.gloryjewel.eatgo.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RestaurantTests {

    @Test
    public void creation(){
        Restaurant restaurant = Restaurant.builder()
                                            .address("Busan")
                                            .name("bob zip")
                                            .build();

        assertEquals(restaurant.getName(),"bob zip");
    }

    @Test
    public void information(){
        Restaurant restaurant = Restaurant.builder()
                                .name("bob zip")
                                .address("in seoul")
                                .build();
        assertEquals(restaurant.getInfomation(),"bob zip in seoul");
    }

}