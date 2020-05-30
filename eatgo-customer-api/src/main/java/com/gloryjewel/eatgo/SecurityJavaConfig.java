package com.gloryjewel.eatgo;

import com.gloryjewel.eatgo.filters.JwtAuthenticationFilter;
import com.gloryjewel.eatgo.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.Filter;

@Configuration
@EnableWebSecurity
@ComponentScan(value = "com.gloryjewel.eatgo.utils")
public class SecurityJavaConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public JwtUtil jwtUtil;

    private Filter filter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        this.filter = new JwtAuthenticationFilter(authenticationManager(),jwtUtil);

        http
                .cors().disable()
                .csrf().disable()
                .formLogin().disable()
                .headers().frameOptions().disable()
                .and()
                .addFilter(filter)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
