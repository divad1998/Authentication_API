package com.aneeque.codingchallenge.security;

import com.aneeque.codingchallenge.service.UserService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final ObjectMapper objectMapper;
    private final String secretKey;

    public SecurityConfig(UserService userService,
                          ObjectMapper objectMapper,
                          @Value("jwt-secret-key") String secretKey) {

        this.userService = userService;
        this.objectMapper = objectMapper;
        this.secretKey = secretKey;
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        
        httpSecurity
            .cors()
                .disable()
            .csrf()
                .disable()
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // don't store sessions in datastore because we are using a stateless security
            .and()
            .addFilter(new JwtAuthenticationFilter(authenticationManager(), objectMapper, secretKey))
            .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/auth/users", "/**").permitAll() // permit all requests
                .antMatchers(HttpMethod.GET, "/**").permitAll()
                .anyRequest().authenticated()
            .and()
            .httpBasic()
                .disable()
            .formLogin()
                .disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userService)
                .passwordEncoder(passwordEncoder());
    }
}