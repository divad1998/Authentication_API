package com.aneeque.codingchallenge;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class LoginBody {

    @NotBlank(message = "Username is required.")
    @NotNull(message = "Username can't be null.")
    private String username;

    @NotBlank(message = "Password is required.")
    @NotNull(message = "Password can't be null.")
    private String password;
    
}