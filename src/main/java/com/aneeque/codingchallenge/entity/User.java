package com.aneeque.codingchallenge.entity;

import java.util.Collection;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public String id;

    private String surname;
    private String firstName;

    @Email(message = "Invalid Email format.")
    @NotNull(message = "Email field can't be null.")
    @NotBlank(message = "Email field can't be empty.")
    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String username;

    @NotNull(message = "Password can't be null.")
    @NotBlank(message = "Password can't be empty.")
    private String password;

    public void setPassword(String password) {
        this.password = new BCryptPasswordEncoder().encode(password);
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        
        return null;
    }

    @Override
    public String getUsername() {
        
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        
        return true;
    }

    @Override
    public boolean isEnabled() {
        
        return true;
    }
    
}