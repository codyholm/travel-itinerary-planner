package com.example.demo.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * DTO for checkout purchase request.
 * Matches frontend snake_case payload structure.
 */
@Data
public class PurchaseDTO {

    @NotNull(message = "Customer information is required")
    @Valid
    private CustomerDTO customer;

    @NotNull(message = "Cart is required")
    @Valid
    private CartDTO cart;

    @NotEmpty(message = "Cart must contain at least one item")
    private List<CartItemDTO> cartItems;
}
