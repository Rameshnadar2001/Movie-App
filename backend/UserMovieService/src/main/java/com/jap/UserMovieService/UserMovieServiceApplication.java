package com.jap.UserMovieService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@SpringBootApplication
@EnableDiscoveryClient
public class UserMovieServiceApplication {
@Bean
public RestTemplate restTemplate(){
	return new RestTemplate();
}
@Bean
public ObjectMapper objectMapper(){
	return new ObjectMapper();
}
	public static void main(String[] args) {
		SpringApplication.run(UserMovieServiceApplication.class, args);
	}

}
