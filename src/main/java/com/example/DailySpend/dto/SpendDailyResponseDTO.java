package com.example.DailySpend.dto;

import java.math.BigDecimal;
import java.util.List;

public record SpendDailyResponseDTO(
        List<SpendDailyItemDTO> spends,
        BigDecimal total
) {}
