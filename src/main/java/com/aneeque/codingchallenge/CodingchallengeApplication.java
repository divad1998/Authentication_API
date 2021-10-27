package com.aneeque.codingchallenge;

import com.aneeque.codingchallenge.jwt.JwtConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@EnableConfigurationProperties(JwtConfig.class)
public class CodingchallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodingchallengeApplication.class, args);
	}
}
