package com.example.CoreUser.Service;

import com.example.CoreUser.DTO.ApiResponse;

public interface PaymentService {
    /**
     * Processes a payment request.
     *
     * @param paymentRequest a JSON string representing the payment request details.
     * @return an ApiResponse object containing the status and details of the payment.
     */
    ApiResponse makePayment(String paymentRequest);
}
