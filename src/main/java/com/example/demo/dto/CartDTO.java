package com.example.demo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

/**
 * DTO for cart in purchase request.
 * Uses snake_case to match frontend payload.
 */
@Data
public class CartDTO {

    @NotNull(message = "Package price is required")
    @Min(value = 0, message = "Package price must be non-negative")
    private BigDecimal package_price;

    @NotNull(message = "Party size is required")
    @Min(value = 1, message = "Party size must be at least 1")
    private Integer party_size;

    private String status;
}
