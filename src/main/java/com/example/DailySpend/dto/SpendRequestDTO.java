package com.example.DailySpend.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record SpendRequestDTO(
        @NotNull String name,
        @NotNull BigDecimal amount,
        @NotNull Integer quantity,
        @NotNull String category
) {
}
