package com.example.demo.dto;

import java.math.BigDecimal;

public record VacationDetailDTO(
        Long id,
        String vacation_title,
        String description,
        BigDecimal travel_price,
        String image_URL
) {}

