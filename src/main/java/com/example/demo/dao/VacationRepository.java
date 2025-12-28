package com.example.demo.dao;

import com.example.demo.entities.Vacation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;

public interface VacationRepository extends JpaRepository<Vacation, Long> {

    @EntityGraph(attributePaths = "excursions")
    List<Vacation> findAllWithExcursions();
}
