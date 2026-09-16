package com.example.DailySpend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record SpendDailyRequestDTO(
        @JsonFormat(pattern = "yyyy-MM-dd")
        @NotNull LocalDate date
) {
}
