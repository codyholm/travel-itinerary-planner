package com.example.demo.dto;

import java.math.BigDecimal;

public record VacationListItemDTO(
        Long id,
        String vacation_title,
        String description,
        BigDecimal travel_price,
        String image_URL,
        int excursions_count
) {}

