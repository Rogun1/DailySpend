package com.example.DailySpend.repository;

import com.example.DailySpend.model.Income;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncomeRepository extends JpaRepository<Income, Long> {

    List<Income> findAllByAccountId(Long accountId);
}
