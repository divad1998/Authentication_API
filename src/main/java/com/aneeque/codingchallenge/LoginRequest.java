package com.aneeque.codingchallenge;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class LoginRequest {

    @NotBlank(message = "Email can't be empty")
    @NotNull(message = "Email can't be null")
    private String email;

    @NotBlank(message = "Password can't be empty")
    @NotNull(message = "Password can't be null")
    private String password;
    
}