package com.example.CoreUser.Respo;


import com.example.CoreUser.Model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<UserDetails, String> {
    UserDetails findByAccountNumber(String accountNumber);
}
