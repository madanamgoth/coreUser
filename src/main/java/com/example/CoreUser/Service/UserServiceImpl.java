package com.example.CoreUser.Service;

import com.example.CoreUser.DTO.ApiResponse;
import com.example.CoreUser.DTO.PaymentRequestDTO;
import com.example.CoreUser.DTO.RegisterUserRequestDTO;
import com.example.CoreUser.Model.UserDetails;
import com.example.CoreUser.RabbitMq.RabbitMQConfig;
import com.example.CoreUser.RabbitMq.RabbitMQSender;
import com.example.CoreUser.Respo.UserRepo;
import com.example.CoreUser.Util.AppUtil;
import com.example.CoreUser.Util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

        @Autowired
        private UserRepo userRepository;

        @Autowired
        private PasswordEncoder passwordEncoder;


        @Override
        public ApiResponse registerUser(String userRequest) {
            try {
                RegisterUserRequestDTO request = JsonUtil.parseJson(userRequest, RegisterUserRequestDTO.class);
                return processRegistration(request);
            } catch (JsonProcessingException e) {
                return new ApiResponse("failed", "Invalid user registration request format");
            } catch (Exception e) {
                return new ApiResponse("failed", "User registration failed");
            }
        }

        public ApiResponse processRegistration(RegisterUserRequestDTO request) {
            if (request.getPin().length() <= 6) {
                ApiResponse response = validateUserDetails(request);
                if (response.getStatus().equals("success")) {
                    return response;
                }
            }
            return new ApiResponse("failed", "User registration failed");
        }

        public ApiResponse validateUserDetails(RegisterUserRequestDTO request) {
            UserDetails user = userRepository.findByAccountNumber(request.getAccountNumber());
            if (user != null) {
                return new ApiResponse("failed", "User already exists");
            }
            UserDetails newUser = new UserDetails();
            newUser.setAccountNumber(request.getAccountNumber());
            newUser.setPin(passwordEncoder.encode(request.getPin()));
            newUser.setFirstName(request.getFirstName());
            newUser.setLastName(request.getLastName());
            userRepository.save(newUser);
            return new ApiResponse("success", "User registered successfully");
        }
}
