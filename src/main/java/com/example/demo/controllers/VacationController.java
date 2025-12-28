package com.example.demo.controllers;

import com.example.demo.dao.ExcursionRepository;
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
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/vacations")
public class VacationController {

    private final VacationRepository vacationRepository;
    private final ExcursionRepository excursionRepository;

    public VacationController(VacationRepository vacationRepository, ExcursionRepository excursionRepository) {
        this.vacationRepository = vacationRepository;
        this.excursionRepository = excursionRepository;
    }

    @GetMapping
    public List<VacationListItemDTO> getVacations() {
        Map<Long, Long> excursionsCountByVacationId = excursionRepository.countExcursionsByVacationId().stream()
                .collect(Collectors.toMap(
                        row -> (Long) row[0],
                        row -> ((Number) row[1]).longValue()
                ));

        return vacationRepository.findAll().stream()
                .sorted(Comparator.comparing(
                        Vacation::getVacation_title,
                        Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER)
                ))
                .map(vacation -> toListItem(
                        vacation,
                        excursionsCountByVacationId.getOrDefault(vacation.getId(), 0L)
                ))
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<VacationDetailDTO> getVacation(@PathVariable("id") Long id) {
        return vacationRepository.findById(id)
                .map(vacation -> ResponseEntity.ok(toDetail(vacation)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    private VacationListItemDTO toListItem(Vacation vacation, long excursionsCount) {
        return new VacationListItemDTO(
                vacation.getId(),
                vacation.getVacation_title(),
                vacation.getDescription(),
                vacation.getTravel_price(),
                vacation.getImage_URL(),
                (int) excursionsCount
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
