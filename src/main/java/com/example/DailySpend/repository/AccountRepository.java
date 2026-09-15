package com.example.DailySpend.repository;

import com.example.DailySpend.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Boolean existsByEmail(String email);
    Optional<Account> findByEmail(String email);
}
