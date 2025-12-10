package com.example.demo.bootstrap;

import com.example.demo.dao.CustomerRepository;
import com.example.demo.dao.DivisionRepository;
import com.example.demo.entities.Customer;
import com.example.demo.entities.Division;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Bootstrap loader for sample customer data.
 * Guards: Skip if any customers exist OR if required divisions are missing.
 * Enabled via: app.bootstrap.enabled=true
 */
@Component
@ConditionalOnProperty(prefix = "app.bootstrap", name = "enabled", havingValue = "true")
public class BootStrapData implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(BootStrapData.class);

    private final CustomerRepository customerRepository;
    private final DivisionRepository divisionRepository;

    public BootStrapData(CustomerRepository customerRepository, DivisionRepository divisionRepository) {
        this.customerRepository = customerRepository;
        this.divisionRepository = divisionRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Guard: Skip if customers already exist
        if (customerRepository.count() > 0) {
            log.info("Skipping bootstrap; {} customers already present", customerRepository.count());
            return;
        }

        // Required divisions aligned with seeded init.sql identifiers
        Long[] requiredDivisionIds = {8L, 40L, 54L, 52L, 101L};
        
        // Guard: Verify all required divisions exist before creating any customers
        for (Long divisionId : requiredDivisionIds) {
            if (!divisionRepository.existsById(divisionId)) {
                log.warn("Skipping bootstrap; required division {} is missing. Ensure init.sql has executed.", divisionId);
                return;
            }
        }

        // Fetch divisions
        Division janeDiv = divisionRepository.findById(8L).orElse(null);
        Division clarkDiv = divisionRepository.findById(40L).orElse(null);
        Division bruceDiv = divisionRepository.findById(54L).orElse(null);
        Division peterDiv = divisionRepository.findById(52L).orElse(null);
        Division jackDiv = divisionRepository.findById(101L).orElse(null);

        // Create sample customers
        createCustomer("Jane", "Doe", "123 Main St", janeDiv);
        createCustomer("Clark", "Kent", "456 Main St", clarkDiv);
        createCustomer("Bruce", "Wayne", "789 Gotham St", bruceDiv);
        createCustomer("Peter", "Parker", "1010 Main St", peterDiv);
        createCustomer("Jack", "Sparrow", "1234 Main St", jackDiv);

        log.info("Bootstrap complete; created {} sample customers", customerRepository.count());
    }

    private void createCustomer(String firstName, String lastName, String address, Division division) {
        if (division == null) {
            log.error("Cannot create customer {} {} because division is null", firstName, lastName);
            return;
        }

        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setAddress(address);
        customer.setPostal_code("12345");
        customer.setPhone("123-456-7890");
        customer.setDivision(division);
        customerRepository.save(customer);
        log.debug("Created customer: {} {}", firstName, lastName);
    }
}