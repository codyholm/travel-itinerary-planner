package com.example.demo.services;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * DTO for checkout purchase request using ID-based references.
 */
@Data
public class PurchaseRequest {

    @NotNull(message = "Customer information is required")
    @Valid
    private CustomerPayload customer;

    @NotEmpty(message = "Cart must contain at least one item")
    @Valid
    private Set<CartItemPayload> cartItems = new HashSet<>();

    @NotNull(message = "Party size is required")
    @Positive(message = "Party size must be greater than zero")
    private Integer partySize;

    @Data
    public static class CustomerPayload {
        @NotBlank(message = "First name is required")
        private String firstName;

        @NotBlank(message = "Last name is required")
        private String lastName;

        @NotBlank(message = "Address is required")
        private String address;

        @NotBlank(message = "Postal code is required")
        private String postalCode;

        @NotBlank(message = "Phone number is required")
        private String phone;

        @NotNull(message = "Division is required")
        @Positive(message = "Division id must be positive")
        private Long divisionId;
    }

    @Data
    public static class CartItemPayload {
        @NotNull(message = "Vacation is required")
        @Positive(message = "Vacation id must be positive")
        private Long vacationId;

        private Set<@Positive Long> excursionIds = new HashSet<>();
    }
}
