package com.aneeque.codingchallenge.utilities;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public class Util {

    public static URI generateUri(String userId) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(userId)
                .toUri();
    }
    
}