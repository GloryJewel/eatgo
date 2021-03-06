package com.gloryjewel.eatgo.interfaces;

import com.gloryjewel.eatgo.application.CategoryService;
import com.gloryjewel.eatgo.application.RegionService;
import com.gloryjewel.eatgo.domain.Category;
import com.gloryjewel.eatgo.domain.Region;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.Arrays;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryController.class)
class CategoryControllerTests {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext ctx;

    @MockBean(CategoryService.class)
    private CategoryService categoryService;

    @BeforeEach
    public void setUp(){

        MockitoAnnotations.initMocks(this);

        this.mvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))  // 필터 추가
                .alwaysDo(print())
                .build();
    }

    @Test
    void list() throws Exception {
        Category category = Category.builder()
                .name("서울")
                .build();

        given(categoryService.getCategories()).willReturn(Arrays.asList(category));

        mvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("서울")));

    }

    @Test
    void create() throws Exception {
        given(categoryService.addCategory("서울")).will(invocation -> {
            String name = invocation.getArgument(0);

            return Category.builder()
                    .id(1L)
                    .name(name)
                    .build();
        });

        mvc.perform(post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"서울\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/categories/1"))
                .andExpect(content().string("{}"));

        verify(categoryService).addCategory("서울");
    }
}