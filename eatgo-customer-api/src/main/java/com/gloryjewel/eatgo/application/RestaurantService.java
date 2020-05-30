package com.gloryjewel.eatgo.application;

import com.gloryjewel.eatgo.domain.*;
import com.gloryjewel.eatgo.domain.MenuItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    RestaurantService(RestaurantRepository restaurantRepository,
                      MenuItemRepository menuItemRepository,
                      ReviewRepository reviewRepository){
        this.restaurantRepository =  restaurantRepository;
        this.menuItemRepository = menuItemRepository;
        this.reviewRepository = reviewRepository;
    }

    public Restaurant getRestaurant(Long id){

        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(()->new RestaurantNotFoundException(id));

        List<MenuItem> menuItems = menuItemRepository.findAllByRestaurantId(id);
        restaurant.setMenuItems(menuItems);

        List<Review> reviews = reviewRepository.findAllByRestaurantId(id);
        restaurant.setReviews(reviews);

        return restaurant;
    }

    public List<Restaurant> getRestaurants(String region, long categoryId) {

        List<Restaurant> restaurants = restaurantRepository.findAllByAddressContainingAndCategoryId(
                region,categoryId);

//        return restaurants.stream()
//                .map(item -> {
//
//                    item.setMenuItems(
//                            menuItemRepository.findAllByRestaurantId(item.getId()));
//                    return item;
//                })
//                .collect(Collectors.toList());

        return restaurants;
    }

    public Restaurant addRestaurant(Restaurant restaurant) {

        return restaurantRepository.save(restaurant);
    }

    @Transactional
    public Restaurant updateRestaurant(long id, String name, String address) {

        Restaurant restaurant = restaurantRepository.findById(id).orElse(null);
        restaurant.updateInformation(name, address);

        return restaurant;
    }
}
