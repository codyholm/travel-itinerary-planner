package com.example.demo.controllers;

import com.example.demo.dao.CountryRepository;
import com.example.demo.dto.CountryOptionDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/countries")
public class CountryController {

    private final CountryRepository countryRepository;

    public CountryController(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @GetMapping
    public List<CountryOptionDTO> getCountries() {
        return countryRepository.findAll().stream()
                .sorted(Comparator.comparing(country -> country.getCountry_name().toLowerCase()))
                .map(country -> new CountryOptionDTO(country.getId(), country.getCountry_name()))
                .toList();
    }
}

