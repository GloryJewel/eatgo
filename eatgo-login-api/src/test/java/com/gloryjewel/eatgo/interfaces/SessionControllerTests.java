package com.gloryjewel.eatgo.interfaces;

import com.gloryjewel.eatgo.application.EmailNotExistedException;
import com.gloryjewel.eatgo.application.PasswordWrongException;
import com.gloryjewel.eatgo.application.UserService;
import com.gloryjewel.eatgo.domain.User;
import com.gloryjewel.eatgo.utils.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.StringContains.containsString;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SessionController.class)
class SessionControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean(UserService.class)
    private UserService userService;

    @MockBean(JwtUtil.class)
    private JwtUtil jwtUtil;

    @Test
    public void createWithValid() throws Exception {
        final String email = "test@email.com";
        final String password = "test";

        given(userService.authenticate(email,password)).willReturn(
                User.builder()
                        .password("ACCESSTOKEN")
                        .name("son")
                        .restaurantId(1L)
                        .id(1L)
                        .build()
        );

        given(jwtUtil.createToken(anyLong(),any(), any())).willReturn("header.payload.signature");

        mvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"test@email.com\",\"password\":\"test\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/session"))
                .andExpect(content().string(containsString("{\"accessToken\":\"header.payload.signature\"")));

        verify(userService).authenticate(email, password);

    }

    @Test
    public void createWithWongPassword() throws Exception {
        given(userService.authenticate("test@email.com", "xx"))
                .willThrow(PasswordWrongException.class);

        mvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"test@email.com\",\"password\":\"xx\"}"))
                .andExpect(status().isBadRequest());

        verify(userService).authenticate("test@email.com","xx");

    }

    @Test
    public void createWithNotExistedEmail() throws Exception {
        given(userService.authenticate("x@email.com", "test"))
                .willThrow(EmailNotExistedException.class);

        mvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"x@email.com\",\"password\":\"test\"}"))
                .andExpect(status().isBadRequest());

        verify(userService).authenticate("x@email.com","test");

    }
}