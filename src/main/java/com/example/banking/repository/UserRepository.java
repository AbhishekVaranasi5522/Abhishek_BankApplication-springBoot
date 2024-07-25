package com.example.banking.repository;

import com.example.banking.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Users,Integer> {

    Optional<Users> findByFirstNameAndLastName(String firstName, String lastName);

    @Query("SELECT u FROM Users u JOIN u.bankAccounts b WHERE b.accountNumber = :accountNumber")
    Users findByAccountNumber(@Param("accountNumber") Long accountNumber);
}
