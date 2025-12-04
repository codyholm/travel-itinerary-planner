package com.example.demo.dao;

import com.example.demo.entities.Vacation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface VacationRepository extends JpaRepository<Vacation, Long> {}