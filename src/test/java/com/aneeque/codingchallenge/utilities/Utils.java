//package com.aneeque.codingchallenge.utilities;
//
//import com.aneeque.codingchallenge.entity.User;
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.algorithms.Algorithm;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//
//import java.sql.Date;
//import java.time.Instant;
//import java.time.LocalDateTime;
//import java.time.ZoneOffset;
//
//public class Utils { // why can't it get the value of secretKey? coz it isn't.
//
//
//
//    public static User createUser() {
//
//        User user = new User();
//        user.setId("1");
//        user.setFirstName("dave");
//        user.setSurname("dee");
//        user.setEmail("d@gmail.com");
//        user.setUsername("davinci");
//        user.setPassword("password");
//
//        return user;
//    }
//
////    public String buildToken(User user, String secretKey) {
////
////        // build jwtToken
////        String token = JWT.create()
////                .withSubject(user.getUsername())
////                .withIssuedAt(Date.from(Instant.now()))
////                .withExpiresAt(java.util.Date.from(LocalDateTime.now().plusMonths(1L).toInstant(ZoneOffset.UTC)))
////                .sign(Algorithm.HMAC512(secretKey.getBytes()));
////
////        String authorization = "Bearer " + token;
////
////        return authorization;
////    }
//}