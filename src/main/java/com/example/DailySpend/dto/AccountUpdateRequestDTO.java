package com.example.DailySpend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AccountUpdateRequestDTO(
        @NotNull @NotBlank String firstName,
        @NotNull @NotBlank String lastName,
        @NotNull @Positive int age
        ) {
}
