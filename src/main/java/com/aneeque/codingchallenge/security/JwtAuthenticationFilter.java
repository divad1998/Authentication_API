package com.aneeque.codingchallenge.security;

import com.aneeque.codingchallenge.LoginRequest;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authManager;
    private final ObjectMapper objectMapper;
    private final String secretKey;

    public JwtAuthenticationFilter(AuthenticationManager authManager,
                                   ObjectMapper objectMapper,
                                   String secretKey) {
        this.authManager = authManager;
        this.objectMapper = objectMapper;
        this.secretKey = secretKey;

        setFilterProcessesUrl("/auth/users");
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            // get request body
            LoginRequest loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);
            // delegate authentication.
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                                                loginRequest.getEmail(),
                                                loginRequest.getPassword());
            return authManager.authenticate(authentication);

        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

//        // generate jwtToken
//        String token = Jwt.builder()
////                                .setSubject(authResult.getName()) //wht does this return?
//                                .setExpiration(Date.valueOf(LocalDate.now().plusMonths(1L)))
//                                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
//                                .compact();
//
//        // add token to response headers
//        response.addHeader("Authorization", "Bearer " + token);

        // generate jwt
        String token = JWT.create()
                .withSubject(authResult.getName())
                .withIssuedAt(Date.from(Instant.now()))
                .withExpiresAt(Date.from(LocalDateTime.now().plusMonths(1L).toInstant(ZoneOffset.UTC))) // what id zoneoffset.UTC
                .sign(Algorithm.HMAC512(secretKey.getBytes()));

        // add token to response headers
        response.addHeader("Authorization", "Bearer " + token);

//        User user = (User) auth.getPrincipal();
//        Date issuedAt = from(Instant.now());
//        Date expiresAt = from(now().plusMinutes(parseLong(tokenExpiresAt)).toInstant(UTC));
//
//        String token = create().withSubject(user.getUsername())
//                .withIssuedAt(issuedAt)
//                .withClaim("first_name", user.getFirstName())
//                .withClaim("last_name", user.getLastName())
//                .withClaim("phone_number", user.getPhoneNumber())
//                .withClaim("role", user.getUserRole().name())
//                .withExpiresAt(expiresAt)
//                .sign(HMAC512(SECRET_KEY.getBytes()));
    }
}