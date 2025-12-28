package com.example.demo.controllers;

import com.example.demo.dao.ExcursionRepository;
import com.example.demo.dto.ExcursionDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/excursions")
public class ExcursionController {

    private final ExcursionRepository excursionRepository;

    public ExcursionController(ExcursionRepository excursionRepository) {
        this.excursionRepository = excursionRepository;
    }

    @GetMapping
    public List<ExcursionDTO> getExcursionsByVacation(@RequestParam("vacationId") Long vacationId) {
        return excursionRepository.findByVacationId(vacationId).stream()
                .sorted(Comparator.comparing(excursion -> excursion.getExcursion_title().toLowerCase()))
                .map(excursion -> new ExcursionDTO(
                        excursion.getId(),
                        excursion.getExcursion_title(),
                        excursion.getExcursion_price(),
                        excursion.getImage_URL()
                ))
                .toList();
    }
}

