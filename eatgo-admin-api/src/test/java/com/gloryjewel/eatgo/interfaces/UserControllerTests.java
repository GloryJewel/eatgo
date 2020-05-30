package com.gloryjewel.eatgo.interfaces;

import com.gloryjewel.eatgo.application.RestaurantService;
import com.gloryjewel.eatgo.application.UserService;
import com.gloryjewel.eatgo.domain.User;
import org.junit.jupiter.api.BeforeAll;
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

import java.util.Arrays;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTests {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext ctx;

    @MockBean(UserService.class)
    UserService userservice;

    @BeforeEach
    public void setUp(){

        this.mvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))  // 필터 추가
                .alwaysDo(print())
                .build();
    }

    @Test
    public void list() throws Exception {

        User user = User.builder()
                        .email("test@gmail.com")
                        .level(50L)
                        .name("테스터")
                        .id(1L)
                        .build();

        given(userservice.getUsers()).willReturn(Arrays.asList(user));

        mvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("테스터")));

    }

    @Test
    public void create() throws Exception {

        String name = "테스터";
        String email = "test@gmail.com";

        given(userservice.addUser(name,email)).will(invocation -> {

            return User.builder()
                    .email(invocation.getArgument(1))
                    .name(invocation.getArgument(0))
                    .id(1L)
                    .build();
        });

        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\""+ email +"\",\"name\":\""+name+"\",\"level\":5}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/users/1"))
                .andExpect(content().string("{}"));
    }

    @Test
    public void update() throws Exception {

        Long id = 1L;
        String name = "테스터";
        String email = "test@gmail.com";
        Long level = 101L;

        mvc.perform(patch("/users/"+id)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":"+id+",\"email\":\""+ email +"\",\"name\":\""+name+"\",\"level\":"+level+"}"))
                .andExpect(status().isOk());

        verify(userservice).updateUser(id,name,email,level);
    }

    @Test
    public void deactivate() throws Exception {
        mvc.perform(delete("/users/1"))
                .andExpect(status().isOk());

        verify(userservice).deactiveUser(1L);
    }

}