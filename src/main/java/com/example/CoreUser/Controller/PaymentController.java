package com.example.CoreUser.Controller;

import com.example.CoreUser.DTO.ApiResponse;
import com.example.CoreUser.Service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/makePayment")
    public ResponseEntity<ApiResponse> makePayment(@RequestBody String paymentRequest) {
        ApiResponse response = paymentService.makePayment(paymentRequest);
        return ResponseEntity.ok(response);
    }
}