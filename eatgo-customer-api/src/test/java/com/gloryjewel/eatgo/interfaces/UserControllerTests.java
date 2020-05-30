package com.gloryjewel.eatgo.interfaces;

import com.gloryjewel.eatgo.application.UserService;
import com.gloryjewel.eatgo.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean(UserService.class)
    private UserService userService;

    @Test
    public void create() throws Exception {

        User user = User.builder()
                .id(1L)
                .email("tester@gamil.com")
                .name("테스터")
                .password("test")
                .build();

        given(userService.registerUser("테스터","test@email.com","test")).willReturn(user);

        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"test@email.com\",\"name\":\"테스터\",\"password\":\"test\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/users/1"))
                .andExpect(content().string("{}"));

    }

}