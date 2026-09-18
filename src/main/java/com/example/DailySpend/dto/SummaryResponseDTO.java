package com.example.DailySpend.dto;

import com.example.DailySpend.constants.SpendCategory;

import java.math.BigDecimal;
import java.util.Map;

public record SummaryResponseDTO(
        Map<SpendCategory, BigDecimal> categories,
        BigDecimal total
) {}