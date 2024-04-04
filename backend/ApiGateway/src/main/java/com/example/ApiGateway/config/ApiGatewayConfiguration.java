package com.example.ApiGateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ApiGatewayConfiguration {
    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder)
    {
             return builder.routes().route(p->p.path("/api/v0/**").uri("lb://user-authentication-service/"))
                                     .route(p->p.path("/api/v2/**").uri("lb://user-movie-service/"))
                                     .route(p->p.path("/api/v1/**").uri("lb://user-movie-recommendation-service/"))
                                     .build();
    }
}
