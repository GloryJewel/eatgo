package com.gloryjewel.eatgo.application;

import com.gloryjewel.eatgo.domain.User;
import com.gloryjewel.eatgo.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public User authenticate(String email, String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new EmailNotExistedException());

        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new PasswordWrongException();
        }

        return user;
    }
}
