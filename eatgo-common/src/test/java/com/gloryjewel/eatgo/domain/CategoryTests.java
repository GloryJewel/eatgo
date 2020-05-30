package com.gloryjewel.eatgo.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTests {

    @Test
    public void create(){

        Category category = Category.builder()
                    .name("중국")
                    .build();

        assertEquals(category.getName(), "중국");
    }
}