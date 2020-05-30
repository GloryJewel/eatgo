package com.gloryjewel.eatgo;

import com.gloryjewel.eatgo.domain.RestaurantRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;

import java.net.URL;

@ActiveProfiles("test")
@SpringBootTest
class EatgoCustomerApplicationTests {

    @Autowired
    private Environment environment;

    @Test
    void contextLoads() {
        System.out.println(environment.getProperty("spring.profiles.active"));
        System.out.println(environment.getProperty("spring.datasource.url"));
        System.out.println("----------------------"+environment);

        System.out.println(System.getProperty("java.class.path"));

        ClassLoader cl;
        cl = Thread.currentThread().getContextClassLoader();
        if( cl == null )
            cl = ClassLoader.getSystemClassLoader();

//        URL url = cl.getResource( "classpath:application1.yml" );
//
//        System.out.println(url);
    }

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    void getSelectQuery(){
        System.out.println("----------------------------------------------");
        System.out.println(environment.getProperty("spring.datasource.url"));
        //List<Restaurant> restaurants = restaurantRepository.findAllByAddressContaining("");
        //System.out.println(restaurants.get(0).getName());
    }
}
