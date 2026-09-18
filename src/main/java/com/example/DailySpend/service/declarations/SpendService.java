package com.example.DailySpend.service.declarations;

import com.example.DailySpend.constants.SpendCategory;
import com.example.DailySpend.dto.*;
import com.example.DailySpend.model.Spend;

import java.math.BigDecimal;
import java.util.Map;

public interface SpendService {

  SpendResponseDTO addSpend(String email, SpendRequestDTO spendRequestDTO);
  SpendDailyResponseDTO getSpendDaily(String email, SpendDailyRequestDTO spendDailyRequestDTO);
  SummaryResponseDTO summary(String email, Long lastDays);


    default SpendResponseDTO spendToDTO(Spend spend){

        BigDecimal totalAmount = spend.getAmount().multiply(BigDecimal.valueOf(spend.getQuantity()));

        String msg =
                "Successfully added " +
                        spend.getName() +
                        " with amount " +
                        totalAmount +
                        " and category: " +
                        spend.getCategory();

        return new SpendResponseDTO(
                msg
        );
    }

    default SpendDailyItemDTO spendDailyItemToDTO(Spend spend) {

        BigDecimal totalAmount = spend.getAmount()
                .multiply(BigDecimal.valueOf(spend.getQuantity()));

        return new SpendDailyItemDTO(
                spend.getName(),
                spend.getAmount(),
                spend.getQuantity(),
                spend.getCategory(),
                totalAmount
        );
    }

    default SummaryResponseDTO summaryToDTO(
            Map<SpendCategory, BigDecimal> categoryAndAmount,
            BigDecimal total
    ) {
        return new SummaryResponseDTO(
                categoryAndAmount,
                total
        );
    }
}
