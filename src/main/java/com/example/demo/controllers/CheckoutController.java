package com.example.demo.controllers;

import com.example.demo.services.CheckoutService;
import com.example.demo.services.Purchase;
import com.example.demo.services.PurchaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/checkout")
@CrossOrigin("http://localhost:4200")

// Controller class for the checkout service
public class CheckoutController {

    private final CheckoutService checkoutService;

    // Constructor for the CheckoutController class
    @Autowired
    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping("/purchase") // Maps POST request to /api/checkout/purchase
    public PurchaseResponse placeOrder(@RequestBody Purchase purchase) {
        // Calls the placeOrder method in the CheckoutService class to place the order
        PurchaseResponse purchaseResponse = checkoutService.placeOrder(purchase);
        return purchaseResponse;
    }
}

