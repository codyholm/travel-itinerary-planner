package com.example.demo.dao;

import com.example.demo.entities.Excursion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExcursionRepository extends JpaRepository<Excursion, Long> {
    
    /**
     * Find all excursions for a vacation.
     */
    List<Excursion> findByVacationId(Long vacationId);

    @Query("select e.vacation.id, count(e) from Excursion e group by e.vacation.id")
    List<Object[]> countExcursionsByVacationId();
}
