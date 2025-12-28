package com.example.demo.dao;

import com.example.demo.entities.Division;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DivisionRepository extends JpaRepository<Division, Long> {
    
    /**
     * Find all divisions for a country.
     */
    List<Division> findByCountryId(Long countryId);
}
