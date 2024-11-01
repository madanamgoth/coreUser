package com.example.CoreUser.Controller;

import com.example.CoreUser.DTO.ApiResponse;
import com.example.CoreUser.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/registerUser")
    public ResponseEntity<ApiResponse> registerUser(@RequestBody String paymentRequest) {
        ApiResponse response = userService.registerUser(paymentRequest);
        return ResponseEntity.ok(response);
    }
}
