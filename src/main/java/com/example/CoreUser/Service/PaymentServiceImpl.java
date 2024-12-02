package com.example.CoreUser.Service;

import com.example.CoreUser.DTO.ApiResponse;
import com.example.CoreUser.DTO.PaymentRequestDTO;
import com.example.CoreUser.Exception.AccountNumberNotFoundException;
import com.example.CoreUser.Exception.ExceptionHandler;
import com.example.CoreUser.Model.UserDetails;
import com.example.CoreUser.RabbitMq.RabbitMQConfig;
import com.example.CoreUser.RabbitMq.RabbitMQSender;
import com.example.CoreUser.Respo.UserRepo;
import com.example.CoreUser.Util.AppUtil;
import com.example.CoreUser.Util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import jdk.jshell.execution.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RabbitMQSender rabbitMQSender;

    @Autowired
    private ExceptionHandler exceptionHandler;

    @Autowired
    public PaymentServiceImpl(UserRepo userRepository, PasswordEncoder passwordEncoder, RabbitMQSender rabbitMQSender) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.rabbitMQSender = rabbitMQSender;
    }

    @Override
    public ApiResponse makePayment(String paymentRequest) {
        try {
            PaymentRequestDTO request = JsonUtil.parseJson(paymentRequest, PaymentRequestDTO.class);
            return processPayment(request);
        } catch (JsonProcessingException e) {
            return new ApiResponse("failed", "Invalid payment request format");
        } catch (AccountNumberNotFoundException e) {
            return exceptionHandler.accountNumberNotFound(e);
        }catch (Exception e){
            return new ApiResponse("failed", "Payment failed");
        }
    }

    public ApiResponse processPayment(PaymentRequestDTO request) throws AccountNumberNotFoundException {
        if (request.getAmount() > 0) {
            ApiResponse response = validateUserInfo(request);
            if (response.getStatus().equals("success")) {
                return response;
            }
        }
        return new ApiResponse("failed", "Payment failed");
    }

    public ApiResponse validateUserInfo(PaymentRequestDTO request) throws AccountNumberNotFoundException {
        UserDetails user = userRepository.findByAccountNumber(request.getSourceAccount());

        if (user == null) {
             throw new AccountNumberNotFoundException("user not found");
            //return new ApiResponse("failed", "User not found");
        }
        //try to validate user pin using bcrypt
        if (!passwordEncoder.matches(request.getPin(), user.getPin())) {
            return new ApiResponse("failed", "User pin is invalid");
        }
        request.setTransactionId(AppUtil.getID("TRN-"));
        sendToQueue(request);
        return new ApiResponse("success", "Payment under processing", request.getTransactionId());
    }

    public void sendToQueue(PaymentRequestDTO request) {
        try {
            String requestJson = JsonUtil.toJson(request);
            rabbitMQSender.send(RabbitMQConfig.QUEUE, requestJson);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert request to JSON", e);
        }
    }

}
