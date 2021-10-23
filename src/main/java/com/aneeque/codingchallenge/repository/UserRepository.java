package com.aneeque.codingchallenge.repository;

import java.util.Optional;

import com.aneeque.codingchallenge.entity.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
    
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
}