package com.example.demo.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * DTO for customer information in purchase request.
 * Uses snake_case to match frontend payload.
 */
@Data
public class CustomerDTO {

    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    @NotBlank(message = "Address is required")
    @Size(min = 5, max = 200, message = "Address must be between 5 and 200 characters")
    private String address;

    @NotBlank(message = "City is required")
    @Size(min = 2, max = 80, message = "City must be between 2 and 80 characters")
    private String city;

    @NotBlank(message = "Postal code is required")
    @Size(min = 3, max = 20, message = "Postal code must be between 3 and 20 characters")
    private String postal_code;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\d{10,15}$", message = "Phone must be 10-15 digits")
    private String phone;

    @NotNull(message = "Division is required")
    @Valid
    private DivisionDTO division;
}
