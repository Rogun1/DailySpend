package com.example.DailySpend.dto;

import com.example.DailySpend.constants.IncomeType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record AddIncomeRequestDTO(
        @NotNull @NotBlank String name,
        @NotNull @NotBlank BigDecimal amount,
        @NotNull IncomeType incomeType
        ) {
}
