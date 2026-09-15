package com.example.DailySpend.repository;

import com.example.DailySpend.model.Spend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpendRepository extends JpaRepository<Spend, Long> {
}
