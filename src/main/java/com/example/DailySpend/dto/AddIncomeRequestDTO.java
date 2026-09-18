package com.example.DailySpend.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record AddIncomeRequestDTO(
        @NotNull String name,
        @NotNull BigDecimal amount
) {
}
