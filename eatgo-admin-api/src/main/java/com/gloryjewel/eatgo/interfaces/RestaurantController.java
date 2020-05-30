package com.gloryjewel.eatgo.interfaces;

import com.gloryjewel.eatgo.application.RestaurantService;
import com.gloryjewel.eatgo.domain.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@CrossOrigin
@RestController
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/restaurants")
    public List<Restaurant> list(){

        List<Restaurant> restaurants = restaurantService.getRestaurants();

        return restaurants;
    }

    @GetMapping("/restaurants/{id}")
    public Restaurant get(@PathVariable("id") Long id){

        Restaurant restaurant = restaurantService.getRestaurant(id);

        return restaurant;
    }

    @PostMapping("/restaurants")
    public ResponseEntity<?> create(@Valid @RequestBody Restaurant resource) throws URISyntaxException {
//    public ResponseEntity<?> create(@RequestBody String resource) throws URISyntaxException {
//        System.out.println(resource);
//        URI location = new URI("/restaurants/" + 1);

        Restaurant restaurant = restaurantService.addRestaurant(
                Restaurant.builder()
                .name(resource.getName())
                .address(resource.getAddress())
                .build());

        URI location = new URI("/restaurants/" + restaurant.getId());
        return ResponseEntity.created(location).body("{}");
    }

    @PatchMapping("/restaurants/{id}")
    public String update(@PathVariable("id") Long id,
                         @Valid @RequestBody Restaurant resource){

        restaurantService.updateRestaurant(id,
                                        resource.getName(),
                                        resource.getAddress());

        return "{}";
    }
}
