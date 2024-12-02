package com.example.CoreUser.Exception;

import com.example.CoreUser.DTO.ApiResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {

    ApiResponse apiResponse;


    public ExceptionHandler(ApiResponse apiResponse) {
        this.apiResponse = apiResponse;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ApiResponse accountNumberNotFound(AccountNumberNotFoundException e){
        apiResponse.setCallbackUrl("error");
        apiResponse.setMessage(e.getMessage());
        apiResponse.setStatus(String.valueOf(e));
        return apiResponse;
    }
}
