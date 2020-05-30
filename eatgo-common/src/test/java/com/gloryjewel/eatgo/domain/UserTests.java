package com.gloryjewel.eatgo.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTests {

    @Test
    public void create(){

        User user = User.builder()
                .email("test@gmail.com")
                .name("테스터")
                .level(1L)
                .build();

        assertEquals(user.getName(), "테스터");
        assertEquals(user.isAdmin(), false);
    }

    @Test
    public void restaurantOwner(){

        User user = User.builder()
                        .level(1L)
                        .build();

        assertEquals(user.isRestaurantOwner(), false);
    }



}