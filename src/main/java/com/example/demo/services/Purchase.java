package com.example.demo.services;

import com.example.demo.entities.Cart;
import com.example.demo.entities.CartItem;
import com.example.demo.entities.Customer;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

/**
 * DTO for checkout purchase request.
 */
@Data
public class Purchase {

    @NotNull(message = "Customer information is required")
    @Valid
    private Customer customer;

    @NotNull(message = "Cart is required")
    private Cart cart;

    @NotEmpty(message = "Cart must contain at least one item")
    private Set<CartItem> cartItems;
}