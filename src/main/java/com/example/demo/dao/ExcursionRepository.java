package com.example.demo.dao;

import com.example.demo.entities.Excursion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface ExcursionRepository extends JpaRepository<Excursion, Long> {
    
    /**
     * Find all excursions for a given vacation.
     * Exposed as /api/excursions/search/findByVacationId?vacationId={id}
     */
    List<Excursion> findByVacationId(@Param("vacationId") Long vacationId);
}