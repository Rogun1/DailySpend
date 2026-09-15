package com.example.DailySpend.repository;

import com.example.DailySpend.model.Income;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncomeRepository extends JpaRepository<Income, Long> {
}
