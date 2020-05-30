package com.gloryjewel.eatgo.interfaces;

import com.gloryjewel.eatgo.application.UserService;
import com.gloryjewel.eatgo.domain.User;
import com.gloryjewel.eatgo.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class SessionController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @PostMapping("/session")
    public ResponseEntity<SessionResponseDto> create(@RequestBody SessionRequestDto resource) throws URISyntaxException {

        User user = userService.authenticate(resource.getEmail(),resource.getPassword());

        String accessToken = jwtUtil.createToken(user.getId(), user.getName(), user.getRestaurantId());

        URI url = new URI("/session");
        return ResponseEntity.created(url).body(
                SessionResponseDto.builder()
                    .accessToken(accessToken)
                    .build());
    }
}
