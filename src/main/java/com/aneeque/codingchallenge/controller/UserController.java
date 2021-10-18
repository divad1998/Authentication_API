package com.aneeque.codingchallenge.controller;

import javax.validation.Valid;

import com.aneeque.codingchallenge.LoginRequest;
import com.aneeque.codingchallenge.Response;
import com.aneeque.codingchallenge.service.UserService;
import com.aneeque.codingchallenge.utilities.Util;
import com.aneeque.codingchallenge.entity.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
        
    }

    // Signup endpoint
    @PostMapping
    public ResponseEntity<?> addUser(@Valid @RequestBody User user) {

        User newUser = userService.addUser(user);

        Response response = new Response("Part of the family now, aren't you? User created.");
        return ResponseEntity
                    .created(Util.generateUri(newUser.getId()))
                    .body(response);
    }

    // // Login endpoint
    // @PostMapping("/login")
    // public ResponseEntity<Response> login(LoginRequest loginRequest) {

    // }

    // Get all users endpoint
    @GetMapping
    public Iterable<User> getUsers() {
        return userService.getUsers();
    }
}