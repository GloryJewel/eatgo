package com.gloryjewel.eatgo.application;

import com.gloryjewel.eatgo.domain.User;
import com.gloryjewel.eatgo.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {

        return (List<User>) userRepository.findAll();
    }

    public User addUser(String name, String email) {
        User user = User.builder()
                            .email(email)
                            .name(name)
                            .level(10L)
                            .build();
        return userRepository.save(user);
    }

    public User updateUser(Long id, String name, String email, Long level) {
        // todo: restaurant 예외 참고
        User user = userRepository.findById(id).orElse(null);

        user.setName(name);
        user.setEmail(email);
        user.setLevel(level);

        return user;
    }

    public User deactiveUser(long id) {

        User user = userRepository.findById(id).orElse(null);
        user.deactivate();

        return user;
    }
}
