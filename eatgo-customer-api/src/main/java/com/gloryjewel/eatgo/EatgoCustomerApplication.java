package com.gloryjewel.eatgo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class EatgoCustomerApplication {

    public static void main(String[] args) {

        SpringApplication.run(EatgoCustomerApplication.class, args);
        System.out.println(System.getProperty("java.class.path"));
    }
}