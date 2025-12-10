package com.example.demo.services;

import lombok.Getter;

/**
 * Response object for purchase operations.
 * Supports structured error codes per SWE §5.2 API Design Standards.
 */
@Getter
public class PurchaseResponse {

    private final boolean success;
    private final String orderTrackingNumber;
    private final String errorCode;
    private final String errorMessage;

    // Private constructor - use static factory methods
    private PurchaseResponse(boolean success, String orderTrackingNumber, String errorCode, String errorMessage) {
        this.success = success;
        this.orderTrackingNumber = orderTrackingNumber;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    // Factory method for successful purchase
    public static PurchaseResponse success(String orderTrackingNumber) {
        return new PurchaseResponse(true, orderTrackingNumber, null, null);
    }

    // Factory method for failed purchase with simple message (backward compatible)
    public static PurchaseResponse error(String errorMessage) {
        return new PurchaseResponse(false, null, "VALIDATION_ERROR", errorMessage);
    }

    // Factory method for failed purchase with error code and message
    public static PurchaseResponse error(String errorCode, String errorMessage) {
        return new PurchaseResponse(false, null, errorCode, errorMessage);
    }
}