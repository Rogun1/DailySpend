package com.example.DailySpend.service.declarations;

import com.example.DailySpend.constants.SpendCategory;
import com.example.DailySpend.dto.*;
import com.example.DailySpend.model.Spend;

import java.util.List;
import java.util.Map;

public interface SpendService {

  SpendResponseDTO addSpend(String email, SpendRequestDTO spendRequestDTO);
  List<SpendDailyResponseDTO> getSpendDaily(String email, SpendDailyRequestDTO spendDailyRequestDTO);
  SummaryResponseDTO summary(String email, Long lastDays);


    default SpendResponseDTO spendToDTO(Spend spend){

        String msg =
                "Successfully added " +
                        spend.getName() +
                        " with amount " +
                        spend.getAmount() +
                        " and category: " +
                        spend.getCategory();

        return new SpendResponseDTO(
                msg
        );
    }

    default SpendDailyResponseDTO spendDailyToDTO(Spend spend){

        String msg =
                "Spend for: " +
                        spend.getName() +
                        ", costs: " +
                        spend.getAmount() +
                        ", for: " +
                        spend.getCategory().name();

        return new SpendDailyResponseDTO(
                msg
        );
    }

    default SummaryResponseDTO summaryToDTO(
            Map<SpendCategory, Double> categoryAndAmount,
            Double total
    ){
        String mapList = "Categories and amounts: " +
                categoryAndAmount;
        String totalAmount = "Total: " + total;

        String msg = mapList + "\n " + totalAmount;

        return new SummaryResponseDTO(
                msg
        );
    }
}
