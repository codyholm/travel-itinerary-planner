package com.example.demo.dao;

import com.example.demo.entities.Division;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface DivisionRepository extends JpaRepository<Division, Long> {
    
    /**
     * Find all divisions for a given country.
     * Exposed as /api/divisions/search/findByCountryId?countryId={id}
     */
    List<Division> findByCountryId(@Param("countryId") Long countryId);
}