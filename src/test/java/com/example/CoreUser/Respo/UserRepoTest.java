package com.example.CoreUser.Respo;

import com.example.CoreUser.Model.UserDetails;
import com.example.CoreUser.helper.SecurityConfig;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@Import(SecurityConfig.class)
class UserRepoTest {


    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @org.junit.jupiter.api.Test
    void findByAccountNumber() {
        UserDetails userDetails = new UserDetails();
        userDetails.setAccountNumber("1234567890");
        userDetails.setFirstName("John");
        userDetails.setLastName("Doe");
        userDetails.setPin(passwordEncoder
                .encode("1234"));
        userRepo.save(userDetails);

        UserDetails userDetails1 = userRepo.findByAccountNumber("1234567890");
        assertEquals(userDetails1.getFirstName(), "John");
    }
}