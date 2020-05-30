package com.gloryjewel.eatgo.application;

import com.gloryjewel.eatgo.domain.User;
import com.gloryjewel.eatgo.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class UserServiceTests {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUP(){
        MockitoAnnotations.initMocks(this);

        userService = new UserService(userRepository);
    }

    @Test
    void getUsers() {

        User user = User.builder()
                        .id(1L)
                        .name("테스터")
                        .level(10L)
                        .email("test@gmail.com")
                        .build();

        given(userRepository.findAll()).willReturn(Arrays.asList(user));

        List<User> users = userService.getUsers();

        assertEquals(users.get(0).getName(), "테스터");
    }

    @Test
    void addUser(){

        given(userRepository.save(any())).will(invocation -> {
            User user = invocation.getArgument(0);

            return User.builder()
                    .level(user.getLevel())
                    .email(user.getEmail())
                    .name(user.getName())
                    .id(1L)
                    .build();
        });

        User user = userService.addUser("테스터", "test@gamil.com");

        assertEquals(user.getId(), 1);
    }

    @Test
    public void updateUser(){

        final Long id = 1L;
        final String name = "슈퍼맨";
        final String email = "test@gmail.com";
        final Long level = 101L;

        given(userRepository.findById(id)).will(invocation -> {
           return Optional.of( User.builder()
                        .id(id)
                        .name("평범남")
                        .email("ttt@tt")
                        .level(1L)
                        .build());
        });

        User user = userService.updateUser(id,name,email,level);

        verify(userRepository).findById(id);

        assertEquals(user.getName(), "슈퍼맨");
    }

    @Test
    public void deactive(){

        given(userRepository.findById(1L)).will(invocation -> {
            return Optional.of( User.builder()
                    .id(200L)
                    .name("평범남")
                    .email("ttt@tt")
                    .level(1L)
                    .build());
        });

        User user = userService.deactiveUser(1L);

        verify(userRepository).findById(1L);

        assertEquals(user.isAdmin(), false);
        assertEquals(user.isActive(), false);

        user.deactivate();

        assertEquals(user.isActive(), false);
    }

}