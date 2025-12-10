package com.example.demo.services;

import com.example.demo.dto.PurchaseDTO;

public interface CheckoutService {

    PurchaseResponse placeOrder(PurchaseDTO purchase);
}