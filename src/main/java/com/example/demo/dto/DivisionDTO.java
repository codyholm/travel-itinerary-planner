package com.example.demo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * DTO for division reference in purchase request.
 * Contains nested country_id for validation.
 */
@Data
public class DivisionDTO {

    @NotNull(message = "Division ID is required")
    private Long id;

    // Optional fields sent by frontend for validation
    private String division_name;

    @NotNull(message = "Country ID is required")
    private Long country_id;
}
