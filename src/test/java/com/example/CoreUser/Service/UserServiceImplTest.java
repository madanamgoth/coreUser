package com.example.CoreUser.Service;

import com.example.CoreUser.DTO.ApiResponse;
import com.example.CoreUser.DTO.RegisterUserRequestDTO;
import com.example.CoreUser.Model.UserDetails;
import com.example.CoreUser.Respo.UserRepo;
import com.example.CoreUser.helper.SecurityConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@Import(SecurityConfig.class)
class UserServiceImplTest {

    @Mock
    UserRepo userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void validateUserDetailsWithNewUser() {
        RegisterUserRequestDTO request = new RegisterUserRequestDTO();
        request.setAccountNumber("1234567890");
        request.setPin("123456");
        request.setFirstName("John");
        request.setLastName("Doe");

        when(userRepository.findByAccountNumber(anyString())).thenReturn(null);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        var response = userService.validateUserDetails(request);

        assertEquals("success", response.getStatus());
        assertEquals("User registered successfully", response.getMessage());
    }

    @Test
    void validateUserDetailsWithExistingUser() {
        RegisterUserRequestDTO request = new RegisterUserRequestDTO();
        request.setAccountNumber("1234567890");
        request.setPin("123456");
        request.setFirstName("John");
        request.setLastName("Doe");

        when(userRepository.findByAccountNumber(anyString())).thenReturn(new UserDetails());

        var response = userService.validateUserDetails(request);

        assertEquals("failed", response.getStatus());
        assertEquals("User already exists", response.getMessage());
    }

    @Test
    void processRegistrationWithPinLengthLessThan6(){
        RegisterUserRequestDTO request = new RegisterUserRequestDTO();
        request.setAccountNumber("1234567890");
        request.setPin("12345567");
        request.setFirstName("John");
        request.setLastName("Doe");

        var response = userService.processRegistration(request);

        assertEquals("failed", response.getStatus(), "Expected registration to fail due to short PIN length");
        assertEquals("User registration failed", response.getMessage(), "Expected failure message due to short PIN length");

    }

    @Test
    void processRegistrationWithCorrectData(){
        RegisterUserRequestDTO request = new RegisterUserRequestDTO();
        request.setAccountNumber("1234567890");
        request.setPin("123456");
        request.setFirstName("John");
        request.setLastName("Doe");

        when(userService.validateUserDetails(request)).thenReturn(new ApiResponse("success", "User registered successfully"));

        var response = userService.processRegistration(request);
        assertEquals("success", response.getStatus());


    }

    @Test
    void registerUserWithInvalidJson(){
        var response = userService.registerUser("invalidJson");
        assertEquals("failed", response.getStatus());
        assertEquals("Invalid user registration request format", response.getMessage());
    }
}