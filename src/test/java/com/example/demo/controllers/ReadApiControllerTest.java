package com.example.demo.controllers;

import com.example.demo.dao.CountryRepository;
import com.example.demo.dao.DivisionRepository;
import com.example.demo.dao.ExcursionRepository;
import com.example.demo.dao.VacationRepository;
import com.example.demo.entities.Country;
import com.example.demo.entities.Division;
import com.example.demo.entities.Excursion;
import com.example.demo.entities.Vacation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class ReadApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private DivisionRepository divisionRepository;

    @Autowired
    private VacationRepository vacationRepository;

    @Autowired
    private ExcursionRepository excursionRepository;

    @Test
    void vacationsEndpointReturnsExcursionCounts() throws Exception {
        Vacation vacation = createVacation("Test Vacation", new BigDecimal("100.00"));
        createExcursion(vacation, "Excursion A", new BigDecimal("25.00"));
        createExcursion(vacation, "Excursion B", new BigDecimal("25.00"));

        mockMvc.perform(get("/api/vacations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].vacation_title").value("Test Vacation"))
                .andExpect(jsonPath("$[0].excursions_count").value(2));
    }

    @Test
    void vacationByIdEndpointReturnsDetails() throws Exception {
        Vacation vacation = createVacation("Test Vacation", new BigDecimal("100.00"));

        mockMvc.perform(get("/api/vacations/{id}", vacation.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(vacation.getId()))
                .andExpect(jsonPath("$.vacation_title").value("Test Vacation"))
                .andExpect(jsonPath("$.excursions_count").doesNotExist());
    }

    @Test
    void excursionsEndpointFiltersByVacationId() throws Exception {
        Vacation vacation = createVacation("Test Vacation", new BigDecimal("100.00"));
        createExcursion(vacation, "Excursion A", new BigDecimal("25.00"));

        mockMvc.perform(get("/api/excursions").param("vacationId", vacation.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].excursion_title").value("Excursion A"));
    }

    @Test
    void countryAndDivisionEndpointsReturnOptions() throws Exception {
        Country country = createCountry("U.S");
        Division division = createDivision(country, "California");

        mockMvc.perform(get("/api/countries"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].country_name").value("U.S"));

        mockMvc.perform(get("/api/divisions").param("countryId", country.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(division.getId()))
                .andExpect(jsonPath("$[0].country_id").value(country.getId()));
    }

    private Country createCountry(String name) {
        Country country = new Country();
        country.setCountry_name(name);
        return countryRepository.save(country);
    }

    private Division createDivision(Country country, String divisionName) {
        Division division = new Division();
        division.setDivision_name(divisionName);
        division.setCountry(country);
        return divisionRepository.save(division);
    }

    private Vacation createVacation(String title, BigDecimal travelPrice) {
        Vacation vacation = new Vacation();
        vacation.setVacation_title(title);
        vacation.setDescription("Test description");
        vacation.setTravel_price(travelPrice);
        vacation.setImage_URL("https://example.com/vacation.jpg");
        return vacationRepository.save(vacation);
    }

    private Excursion createExcursion(Vacation vacation, String title, BigDecimal price) {
        Excursion excursion = new Excursion();
        excursion.setVacation(vacation);
        excursion.setExcursion_title(title);
        excursion.setExcursion_price(price);
        excursion.setImage_URL("https://example.com/excursion.jpg");
        return excursionRepository.save(excursion);
    }
}

