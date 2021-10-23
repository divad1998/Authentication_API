package com.aneeque.codingchallenge.jwt;

import com.aneeque.codingchallenge.entity.User;
import com.aneeque.codingchallenge.service.UserService;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.google.common.base.Strings;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.auth0.jwt.JWT.require;
import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

public class JwtTokenVerifier extends OncePerRequestFilter { // ensures filter is ran once for each request

    private final UserService userService;
    private final JwtConfig jwtConfig;
    private final Algorithm algorithm;

    public JwtTokenVerifier(UserService userService,
                            JwtConfig jwtConfig,
                            Algorithm algorithm) {
        this.userService = userService;
        this.jwtConfig = jwtConfig;
        this.algorithm = algorithm;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authorization = req.getHeader("Authorization");
        if (Strings.isNullOrEmpty(authorization) || !authorization.startsWith("Bearer ")) {

            filterChain.doFilter(req, res);

        } else {

            UsernamePasswordAuthenticationToken authentication = getAuthentication(authorization);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(req, res);
        }
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String authorization) {

        String token = authorization.replace("Bearer ", "");
        User user = null;

        try {
            String username = require(algorithm)
                                .build()
                                .verify(token)
                                .getSubject();

            if (!Strings.isNullOrEmpty(username)) {

                user = userService.getByUsername(username);
            }

        } catch (JWTVerificationException ex) {

            throw new RuntimeException(String.format("Token, %s, is invalid.", token));
        }

        assert user != null;
        return new UsernamePasswordAuthenticationToken(user.getUsername(), null, null); // don't forget Admin authority
    }
}