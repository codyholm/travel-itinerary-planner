package com.example.demo.config;

import com.example.demo.entities.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

/**
 * Configuration for Spring Data REST endpoints.
 * Exposes entity IDs in JSON responses and configures CORS.
 */
@Configuration
public class RestDataConfig implements RepositoryRestConfigurer {

    @Value("${app.cors.allowed-origins:http://localhost:4200}")
    private String allowedOrigins;

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        // Expose IDs for all entities
        config.exposeIdsFor(Country.class);
        config.exposeIdsFor(Customer.class);
        config.exposeIdsFor(Division.class);
        config.exposeIdsFor(Excursion.class);
        config.exposeIdsFor(Vacation.class);

        // Configure pagination defaults
        config.setDefaultPageSize(Integer.MAX_VALUE);
        config.setMaxPageSize(Integer.MAX_VALUE);

        // Configure CORS for all endpoints
        cors.addMapping("/api/**")
                .allowedOrigins(allowedOrigins.split(","))
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}

