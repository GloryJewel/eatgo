package com.gloryjewel.eatgo.interfaces;

import com.gloryjewel.eatgo.application.UserService;
import com.gloryjewel.eatgo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> list(){
        return userService.getUsers();
    }

    @PostMapping("/users")
    public ResponseEntity create(@RequestBody User resource) throws URISyntaxException {

        User user = userService.addUser(resource.getName(),resource.getEmail());

        URI location = new URI("/users/" + user.getId());
        return ResponseEntity.created(location).body("{}");
    }

    @PatchMapping("/users/{id}")
    public String update(@PathVariable("id") Long id,
                       @RequestBody User resource){
        userService.updateUser(id,
                                resource.getName(),
                                resource.getEmail(),
                                resource.getLevel());

        return "{}";
    }

    @DeleteMapping("/users/{id}")
    public String delete(@PathVariable("id") Long id){

        userService.deactiveUser(id);

        return "{}";
    }
}
