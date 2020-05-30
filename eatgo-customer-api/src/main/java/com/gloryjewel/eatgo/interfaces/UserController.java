package com.gloryjewel.eatgo.interfaces;

import com.gloryjewel.eatgo.application.UserService;
import com.gloryjewel.eatgo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/users")
    public ResponseEntity create(@RequestBody User resource) throws URISyntaxException {

        User user = userService.registerUser(resource.getName(),
                                            resource.getEmail(),
                                            resource.getPassword());

        URI url = new URI("/users/" + user.getId());
        return ResponseEntity.created(url).body("{}");
    }

}
