package com.example.demo.dao;

import com.example.demo.entities.Division;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface DivisionRepository extends JpaRepository<Division, Long> {}