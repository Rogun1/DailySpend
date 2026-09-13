package com.example.DailySpend.repository;

import com.example.DailySpend.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Boolean existsByEmail(String email);
}
