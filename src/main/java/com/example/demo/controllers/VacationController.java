package com.example.demo.controllers;

import com.example.demo.dao.VacationRepository;
import com.example.demo.dto.VacationDetailDTO;
import com.example.demo.dto.VacationListItemDTO;
import com.example.demo.entities.Vacation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/vacations")
public class VacationController {

    private final VacationRepository vacationRepository;

    public VacationController(VacationRepository vacationRepository) {
        this.vacationRepository = vacationRepository;
    }

    @GetMapping
    public List<VacationListItemDTO> getVacations() {
        return vacationRepository.findAllWithExcursions().stream()
                .sorted(Comparator.comparing(vacation -> vacation.getVacation_title().toLowerCase()))
                .map(this::toListItem)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<VacationDetailDTO> getVacation(@PathVariable("id") Long id) {
        return vacationRepository.findById(id)
                .map(vacation -> ResponseEntity.ok(toDetail(vacation)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    private VacationListItemDTO toListItem(Vacation vacation) {
        int excursionsCount = vacation.getExcursions() == null ? 0 : vacation.getExcursions().size();
        return new VacationListItemDTO(
                vacation.getId(),
                vacation.getVacation_title(),
                vacation.getDescription(),
                vacation.getTravel_price(),
                vacation.getImage_URL(),
                excursionsCount
        );
    }

    private VacationDetailDTO toDetail(Vacation vacation) {
        return new VacationDetailDTO(
                vacation.getId(),
                vacation.getVacation_title(),
                vacation.getDescription(),
                vacation.getTravel_price(),
                vacation.getImage_URL()
        );
    }
}

