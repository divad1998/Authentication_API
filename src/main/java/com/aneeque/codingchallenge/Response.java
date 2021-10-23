package com.aneeque.codingchallenge;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@JsonInclude
public class Response {

    private String message;
    private String jwtToken;

    public Response(String message, String jwtToken) {
        this.message = message;
        this.jwtToken = jwtToken;
    }
    
}