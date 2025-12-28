package com.example.demo.dto;

import java.math.BigDecimal;

public record ExcursionDTO(
        Long id,
        String excursion_title,
        BigDecimal excursion_price,
        String image_URL
) {}

