package com.aneeque.codingchallenge.security;

import com.aneeque.codingchallenge.jwt.JwtAuthenticationFilter;
import com.aneeque.codingchallenge.jwt.JwtConfig;
import com.aneeque.codingchallenge.jwt.JwtTokenVerifier;
import com.aneeque.codingchallenge.service.UserService;

import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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

@EnableWebSecurity
@EnableConfigurationProperties(value = JwtConfig.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final ObjectMapper objectMapper;
    private final JwtConfig jwtConfig;
    private final Algorithm algorithm;

    public SecurityConfig(UserService userService,
                          ObjectMapper objectMapper,
                          JwtConfig jwtConfig,
                          Algorithm algorithm) {

        this.userService = userService;
        this.objectMapper = objectMapper;
        this.jwtConfig = jwtConfig;
        this.algorithm = algorithm;
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
            .addFilter(new JwtAuthenticationFilter(authenticationManager(), objectMapper, jwtConfig, algorithm))
            .addFilterAfter(new JwtTokenVerifier(userService, jwtConfig, algorithm), JwtAuthenticationFilter.class)
            .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/v1/users", "/login").permitAll()
                .anyRequest().authenticated();
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