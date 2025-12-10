package com.example.demo.config;

import com.example.demo.entities.Country;
import com.example.demo.entities.Customer;
import com.example.demo.entities.Division;
import com.example.demo.entities.Excursion;
import com.example.demo.entities.Vacation;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

/**
 * Configuration for Spring Data REST endpoints.
 * Exposes entity IDs in JSON responses and configures pagination.
 * CORS is now handled centrally in WebConfig.
 */
@Configuration
public class RestDataConfig implements RepositoryRestConfigurer {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        // Expose IDs for all entities
        config.exposeIdsFor(Country.class);
        config.exposeIdsFor(Customer.class);
        config.exposeIdsFor(Division.class);
        config.exposeIdsFor(Excursion.class);
        config.exposeIdsFor(Vacation.class);

        // Configure pagination defaults - tightened per SWE standards
        config.setDefaultPageSize(20);
        config.setMaxPageSize(100);
    }
}


