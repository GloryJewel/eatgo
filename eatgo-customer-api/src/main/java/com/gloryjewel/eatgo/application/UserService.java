package com.gloryjewel.eatgo.application;

import com.gloryjewel.eatgo.domain.User;
import com.gloryjewel.eatgo.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserService {

    UserRepository userRepository;

    PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(String name, String email, String password) {
        Optional<User> existed = userRepository.findByEmail(email);

        if(existed.isPresent())
            throw new EmailExistedException(email);

        String encodedPassword = passwordEncoder.encode(password);

        return this.userRepository.save(User.builder()
                                    .name(name)
                                    .email(email)
                                    .password(encodedPassword)
                                    .level(20L)
                                    .build());
    }
}
