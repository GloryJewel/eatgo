package com.gloryjewel.eatgo.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.graalvm.compiler.lir.CompositeValue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import java.security.Key;

@Component
public class JwtUtil {

    private Key key;

    public JwtUtil(@Value("${jwt.secret}") String secretKey) {

        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String createToken(long userId, String name,Long restaurantId) {

        String token = Jwts.builder()
                .claim("userId",userId)
                .claim("name",name)
                .claim("restaurantId", restaurantId)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
        return token;
    }

    public Claims getClaims(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build().parseClaimsJws(token).getBody();
    }
}
