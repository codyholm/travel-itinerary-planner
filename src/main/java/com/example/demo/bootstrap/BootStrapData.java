package com.example.demo.bootstrap;

import com.example.demo.dao.CustomerRepository;
import com.example.demo.dao.DivisionRepository;
import com.example.demo.entities.Customer;
import com.example.demo.entities.Division;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootStrapData implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final DivisionRepository divisionRepository;

    public BootStrapData(CustomerRepository customerRepository, DivisionRepository divisionRepository) {
        this.customerRepository = customerRepository;
        this.divisionRepository = divisionRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if(customerRepository.count() <= 1) {

            // Declares divisions for each sample customer
            Division janeDiv = divisionRepository.findById(8L).orElse(null);
            Division clarkDiv = divisionRepository.findById(40L).orElse(null);
            Division bruceDiv = divisionRepository.findById(170L).orElse(null);
            Division peterDiv = divisionRepository.findById(52L).orElse(null);
            Division jackDiv = divisionRepository.findById(107L).orElse(null);

            // Creates sample customers with necessary fields
            Customer jane = new Customer();
            jane.setFirstName("Jane");
            jane.setLastName("Doe");
            jane.setAddress("123 Main St");
            jane.setPostal_code("12345");
            jane.setPhone("123-456-0987");
            jane.setDivision(janeDiv);
            customerRepository.save(jane);

            Customer clark = new Customer();
            clark.setFirstName("Clark");
            clark.setLastName("Kent");
            clark.setAddress("456 Main St");
            clark.setPostal_code("12345");
            clark.setPhone("123-456-7890");
            clark.setDivision(clarkDiv);
            customerRepository.save(clark);

            Customer bruce = new Customer();
            bruce.setFirstName("Bruce");
            bruce.setLastName("Wayne");
            bruce.setAddress("789 Gotham St");
            bruce.setPostal_code("12345");
            bruce.setPhone("123-456-9087");
            bruce.setDivision(bruceDiv);
            customerRepository.save(bruce);

            Customer peter = new Customer();
            peter.setFirstName("Peter");
            peter.setLastName("Parker");
            peter.setAddress("1010 Main St");
            peter.setPostal_code("12345");
            peter.setPhone("123-654-0987");
            peter.setDivision(peterDiv);
            customerRepository.save(peter);

            Customer jack = new Customer();
            jack.setFirstName("Jack");
            jack.setLastName("Sparrow");
            jack.setAddress("1234 Main St");
            jack.setPostal_code("12345");
            jack.setPhone("321-456-0987");
            jack.setDivision(jackDiv);
            customerRepository.save(jack);

        }

    }
}