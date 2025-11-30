package com.example.demo.services;

import lombok.Getter;

@Getter
public class PurchaseResponse {

    private final boolean success;
    private final String orderTrackingNumber;
    private final String errorMessage;

    // Private constructor - use static factory methods
    private PurchaseResponse(boolean success, String orderTrackingNumber, String errorMessage) {
        this.success = success;
        this.orderTrackingNumber = orderTrackingNumber;
        this.errorMessage = errorMessage;
    }

    // Factory method for successful purchase
    public static PurchaseResponse success(String orderTrackingNumber) {
        return new PurchaseResponse(true, orderTrackingNumber, null);
    }

    // Factory method for failed purchase
    public static PurchaseResponse error(String errorMessage) {
        return new PurchaseResponse(false, null, errorMessage);
    }
}