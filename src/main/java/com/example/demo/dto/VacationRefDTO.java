package com.example.demo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * DTO for vacation reference in cart item.
 */
@Data
public class VacationRefDTO {

    @NotNull(message = "Vacation ID is required")
    private Long id;
}
