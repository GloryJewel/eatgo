package com.gloryjewel.eatgo.application;

import com.gloryjewel.eatgo.domain.User;
import com.gloryjewel.eatgo.domain.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class UserServiceTests {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        this.userService = new UserService(userRepository
        ,passwordEncoder);
    }

    @Test
    void optionalNull(){

        Optional<?> op = Optional.empty();

        assertEquals(op.isPresent(), false);
    }

    @Test
    void authenticateWithValid(){
        final String email = "test@gamil.com";
        final String password = "test";

        given(userRepository.findByEmail(email)).willReturn(
                Optional.of(
                        User.builder()
                            .email(email)
                            .build())
        );

        given(passwordEncoder.matches(any(),any())).willReturn(true);

        User user = userService.authenticate(email,password);

        assertEquals(user.getEmail(), email);
    }

    @Test
    void authenticateWithInValidEmail(){
        final String email = "test@gamil.com";
        final String password = "test";

        given(userRepository.findByEmail(email)).willReturn(
                Optional.empty()
        );


        Assertions.assertThrows(EmailNotExistedException.class, () -> {
            userService.authenticate(email,password);
        });

    }

    @Test
    void authenticateWithInValidPassword(){
        final String email = "test@gamil.com";
        final String password = "test";

        given(userRepository.findByEmail(email)).willReturn(
                Optional.of(
                        User.builder()
                                .email(email)
                                .build())
        );

        Assertions.assertThrows(PasswordWrongException.class, () -> {
            userService.authenticate(email,password);
        });

    }

}