package com.example.demo.dao;

import com.example.demo.entities.Excursion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExcursionRepository extends JpaRepository<Excursion, Long> {
    
    /**
     * Find all excursions for a vacation.
     */
    List<Excursion> findByVacationId(Long vacationId);

    long countByVacationId(Long vacationId);
}
