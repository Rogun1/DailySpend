package com.example.DailySpend.dto;

import jakarta.validation.constraints.NotNull;

public record SpendRequestDTO(
        @NotNull String name,
        @NotNull Double amount,
        @NotNull String category
) {
}
