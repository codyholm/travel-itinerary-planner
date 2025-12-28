package com.example.demo.controllers;

import com.example.demo.dao.DivisionRepository;
import com.example.demo.dto.DivisionOptionDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/divisions")
public class DivisionController {

    private final DivisionRepository divisionRepository;

    public DivisionController(DivisionRepository divisionRepository) {
        this.divisionRepository = divisionRepository;
    }

    @GetMapping
    public List<DivisionOptionDTO> getDivisionsByCountry(@RequestParam("countryId") Long countryId) {
        return divisionRepository.findByCountryId(countryId).stream()
                .sorted(Comparator.comparing(
                        division -> division.getDivision_name(),
                        Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER)
                ))
                .map(division -> new DivisionOptionDTO(division.getId(), division.getDivision_name(), division.getCountry_id()))
                .toList();
    }
}
