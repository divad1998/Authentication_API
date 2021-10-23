//package com.aneeque.codingchallenge.integrationTest;
//
//import com.aneeque.codingchallenge.LoginRequest;
//import com.aneeque.codingchallenge.entity.User;
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.algorithms.Algorithm;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.web.server.LocalServerPort;
//import org.springframework.http.*;
//
//import java.net.URI;
//import java.sql.Date;
//import java.time.Instant;
//import java.time.LocalDateTime;
//import java.time.ZoneOffset;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
//public class IntegrationTests {
//
//    @Autowired
//    private TestRestTemplate template;
//
//    @LocalServerPort
//    private int port;
//
////    @Value("jwt-secret-key")
////    private String secretKey;
//
//    private String jwtToken;
//
//    @Test
//    public void testAddUser() {
//
//        User user = new User();
//        user.setId("1");
//        user.setFirstName("dave");
//        user.setSurname("dee");
//        user.setEmail("d@gmail.com");
//        user.setUsername("davinci");
//        user.setPassword("password");
//
//        HttpEntity<User> entity = new HttpEntity<>(user, getHeaders());
//
//        URI uri = URI.create("http://localhost:" + port + "/api/v1/users");
//        ResponseEntity<String> response = template.exchange(uri, HttpMethod.POST, entity, String.class);
//
//        assertThat(response.getStatusCode().equals(HttpStatus.CREATED));
//        assertThat(response.getBody().equals("User created. Welcome!"));
//    }
//
//    @Test
//    public void testLogin() {
//
//        LoginRequest loginRequest = new LoginRequest();
//        loginRequest.setUsername("davinci");
//        loginRequest.setPassword("password");
//
//        HttpEntity<LoginRequest> entity = new HttpEntity<>(loginRequest, getHeaders());
//
//        URI uri = URI.create("http://localhost:" + port + "/login");
//        ResponseEntity<String> response = template.exchange(uri, HttpMethod.POST, entity, String.class);
//
//        assertThat(response.getStatusCode().equals(HttpStatus.OK));
//        assertThat(response.getHeaders().containsKey("Authorization"));
//
//        List<String> value = response.getHeaders().getOrEmpty("Authorization");
//        assertThat(!value.isEmpty());
//        assertThat(value.size() == 1);
//        assertThat(value.get(0).contains("Bearer "));
//
//        for (String s : value) {
//            jwtToken = s;
//        }
//    }
//
//    @Test
//    public void testGetUsers() {
//
//        User user = new User();
//        user.setId("1");
//        user.setFirstName("dave");
//        user.setSurname("dee");
//        user.setEmail("d@gmail.com");
//        user.setUsername("davinci");
//        user.setPassword("password");
//
////        String token = JWT.create()
////                .withSubject(user.getUsername())
////                .withIssuedAt(Date.from(Instant.now()))
////                .withExpiresAt(java.util.Date.from(LocalDateTime.now().plusMonths(1L).toInstant(ZoneOffset.UTC)))
////                .sign(Algorithm.HMAC512(jwtToken.getBytes()));
//
//        //String jwtToken = "Bearer " + token;
//        HttpHeaders headers = getHeaders();
//        headers.add("Authorization", jwtToken);
//
//        HttpEntity<LoginRequest> entity = new HttpEntity<>(null, headers);
//
//        URI uri = URI.create("http://localhost:" + port + "/api/v1/users");
//        ResponseEntity<String> response = template.exchange(uri, HttpMethod.GET, entity, String.class);
//
//        assertThat(response.getStatusCode().equals(HttpStatus.OK));
//    }
//
//    public HttpHeaders getHeaders() {
//        List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
//        acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.setAccept(acceptableMediaTypes);
//
//        return headers;
//    }
//
//}
