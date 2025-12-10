package com.example.demo.controllers;

import com.example.demo.dto.PurchaseDTO;
import com.example.demo.services.CheckoutService;
import com.example.demo.services.PurchaseResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for checkout operations.
 * Accepts PurchaseDTO matching frontend snake_case payload structure.
 */
@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    private final CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping("/purchase")
    public ResponseEntity<PurchaseResponse> placeOrder(@Valid @RequestBody PurchaseDTO purchase) {
        PurchaseResponse response = checkoutService.placeOrder(purchase);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }
}

