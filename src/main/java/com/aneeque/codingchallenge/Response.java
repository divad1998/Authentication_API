package com.aneeque.codingchallenge;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@JsonInclude
public class Response {

    private String message;

    public Response(String message) {
        this.message = message;
    }
    
}