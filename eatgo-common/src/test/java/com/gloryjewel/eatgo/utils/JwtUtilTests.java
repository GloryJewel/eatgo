package com.gloryjewel.eatgo.utils;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTests {

    private final String secretKey = "12345678901234567890123456789012";

    private JwtUtil jwtUtil;

    @BeforeEach
    public void init(){

        jwtUtil = new JwtUtil(secretKey);
    }

    @Test
    public void createToken(){

        String token = jwtUtil.createToken(1L,"Son");

        assertTrue(containsString(".").matches(token));
    }

    @Test
    public void testByte() throws UnsupportedEncodingException {

        byte[] byteAlphabet = "a".getBytes();

        byte[] byteHangl = "한".getBytes();

        System.out.println(byteAlphabet.length +"," + byteHangl.length );
    }

    @Test
    public void getClaims(){

        String token = jwtUtil.createToken(1L,"Son");

        Claims claims = jwtUtil.getClaims(token);

        assertEquals(claims.get("name"), "Son");
    }
}