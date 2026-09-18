package com.example.DailySpend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record AccountRequestDTO(
        @NotNull String firstName,
        @NotNull String lastName,
        @Email String email,
        @NotNull String pwd,
        @NotNull int age
) {
}
