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
    void registerUser() {
        final User resource = User.builder()
                        .email("test@gamil.com")
                        .name("테스터")
                        .password("test")
                        .build();

        given(userRepository.save(any())).will(invocation -> {
            return resource;
        });

        given(userRepository.findByEmail("test@gamil.com")).willReturn(Optional.empty());

        User user = userService.registerUser(resource.getName(),resource.getEmail(),resource.getPassword());

        assertEquals(user.getName(), "테스터");
    }

    @Test
    void registerUserWithExistedEmail() {

        final String email = "test@gamil.com";
        final String name = "테스터";
        final String password = "test";


        given(userRepository.findByEmail(email)).will(invocation -> {
            return Optional.of(User.builder()
                    .name("미연")
                    .email(email)
                    .level(20L)
                    .build());
        });

        Assertions.assertThrows(EmailExistedException.class, () -> {
            User user = userService.registerUser(name,email,password);
        });

        verify(userRepository,never()).save(any());
    }

    @Test
    void optionalNull(){

        Optional<?> op = Optional.empty();

        assertEquals(op.isPresent(), false);
    }
}