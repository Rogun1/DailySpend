package com.example.DailySpend.dto;

import com.example.DailySpend.constants.SpendCategory;

import java.util.Map;

public record SummaryResponseDTO(
        Map<SpendCategory, Double> categories,
        Double total
) {}