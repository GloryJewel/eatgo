package com.gloryjewel.eatgo.interfaces;

import com.gloryjewel.eatgo.application.RegionService;
import com.gloryjewel.eatgo.domain.Region;
import com.gloryjewel.eatgo.domain.RegionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RegionController.class)
class RegionControllerTests {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext ctx;

    @MockBean(RegionService.class)
    private RegionService regionService;

    @BeforeEach
    public void setUp(){

        MockitoAnnotations.initMocks(this);

        this.mvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))  // 필터 추가
                .alwaysDo(print())
                .build();
    }

    @Test
    public void list() throws Exception {
        Region region = Region.builder()
                .name("서울")
                .build();

        given(regionService.getRegions()).willReturn(Arrays.asList(region));

        mvc.perform(get("/regions"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("서울")));

    }

    @Test
    public void create() throws Exception {

        given(regionService.addRegion("서울")).will(invocation -> {
            String name = invocation.getArgument(0);

            return Region.builder()
                            .id(1L)
                            .name(name)
                            .build();
        });

        mvc.perform(post("/regions")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"서울\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/regions/1"))
                .andExpect(content().string("{}"));

        verify(regionService).addRegion("서울");
    }

}