package com.example.DailySpend.dto;

import com.example.DailySpend.constants.SpendCategory;

import java.math.BigDecimal;

public record SpendDailyItemDTO(
        String name,
        BigDecimal amount,
        Integer quantity,
        SpendCategory category,
        BigDecimal totalAmount
) {}