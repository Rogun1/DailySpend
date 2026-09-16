package com.example.DailySpend.repository;

import com.example.DailySpend.model.Spend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface SpendRepository extends JpaRepository<Spend, Long> {

    List<Spend> findAllByAccountIdAndSpendDate(Long accountId, LocalDate date);
}
