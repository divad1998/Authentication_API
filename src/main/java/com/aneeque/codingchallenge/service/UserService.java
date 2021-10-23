package com.aneeque.codingchallenge.service;

import java.util.ArrayList;
import java.util.List;

import com.aneeque.codingchallenge.entity.*;
import com.aneeque.codingchallenge.repository.UserRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User addUser(User user) {

        return userRepository.save(user);

    }

    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                                .orElseThrow(() -> new UsernameNotFoundException("User not found."));
    }

    public Iterable<User> getUsers() {

        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));
    }
}