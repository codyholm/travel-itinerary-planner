package com.example.demo.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * DTO for cart item in purchase request.
 * Contains vacation and excursion ID references.
 */
@Data
public class CartItemDTO {

    @NotNull(message = "Vacation is required")
    @Valid
    private VacationRefDTO vacation;

    private List<ExcursionRefDTO> excursions;
}
