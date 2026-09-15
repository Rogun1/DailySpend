package com.example.DailySpend.dto;

import jakarta.validation.constraints.NotNull;

public record AddIncomeRequestDTO(
        @NotNull String name,
        @NotNull Double amount
) {
}
