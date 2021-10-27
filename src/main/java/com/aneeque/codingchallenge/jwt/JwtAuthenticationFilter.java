package com.aneeque.codingchallenge.jwt;

import com.aneeque.codingchallenge.LoginBody;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private final JwtConfig jwtConfig;
    private final Algorithm algorithm;

    public JwtAuthenticationFilter(AuthenticationManager authManager,
                                   ObjectMapper objectMapper,
                                   JwtConfig jwtConfig,
                                   Algorithm algorithm) {
        this.authManager = authManager;
        this.objectMapper = objectMapper;
        this.jwtConfig = jwtConfig;
        this.algorithm = algorithm;

        setFilterProcessesUrl("/auth/users");

    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            // get request body
            LoginBody loginBody = objectMapper.readValue(request.getInputStream(), LoginBody.class);
            // delegate authentication.
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                                                loginBody.getUsername(),
                                                loginBody.getPassword());

            return authManager.authenticate(authentication);

        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        // generate jwt
        String token = JWT.create()
                .withSubject(authResult.getName())
                .withIssuedAt(Date.from(Instant.now()))
                .withExpiresAt(Date.from(LocalDateTime.now().plusMonths(jwtConfig.getTokenExpiresAfterMonth()).toInstant(ZoneOffset.UTC))) // what is zoneoffset.UTC
                .sign(algorithm);

        // add token to response headers
        response.addHeader(jwtConfig.getAuthorizationHeader(), jwtConfig.getTokenPrefix() + token);
    }
}