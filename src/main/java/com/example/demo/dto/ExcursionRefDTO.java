package com.example.demo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * DTO for excursion reference in cart item.
 */
@Data
public class ExcursionRefDTO {

    @NotNull(message = "Excursion ID is required")
    private Long id;
}
