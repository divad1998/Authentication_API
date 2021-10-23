package com.aneeque.codingchallenge.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import javax.validation.constraints.NotNull;


@ConfigurationProperties(prefix = "application.jwt", ignoreUnknownFields = false)
@Data
public class JwtConfig {

    @NotNull
    private String secretKey;
    private String tokenPrefix;
    private Long tokenExpiresAfterMonth;

    public JwtConfig() {}

    public String getAuthorizationHeader() {

        return HttpHeaders.AUTHORIZATION;
    }

}
