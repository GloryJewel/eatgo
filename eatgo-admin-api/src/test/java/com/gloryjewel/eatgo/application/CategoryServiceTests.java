package com.gloryjewel.eatgo.application;

import com.gloryjewel.eatgo.domain.Category;
import com.gloryjewel.eatgo.domain.CategoryRepository;
import com.gloryjewel.eatgo.domain.Region;
import com.gloryjewel.eatgo.domain.RegionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

class CategoryServiceTests {

    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        categoryService = new CategoryService(categoryRepository);
    }

    @Test
    void getCategories() {

        Category category = Category.builder()
                .name("서울")
                .build();
        given(categoryRepository.findAll()).willReturn(Arrays.asList(category));

        List<Category> categories = categoryService.getCategories();

        assertEquals(categories.get(0).getName(), "서울");
    }

    @Test
    void addCategory() {

        given(categoryRepository.save(any())).will(invocation -> {

            Category category = invocation.getArgument(0);

            return Category.builder()
                    .name(category.getName())
                    .id(1L)
                    .build();
        });

        Category category = categoryService.addCategory("서울");

        assertEquals(category.getId(), 1);
    }
}