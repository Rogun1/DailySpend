package com.example.DailySpend.service.declarations;

import com.example.DailySpend.dto.SpendDailyRequestDTO;
import com.example.DailySpend.dto.SpendDailyResponseDTO;
import com.example.DailySpend.dto.SpendRequestDTO;
import com.example.DailySpend.dto.SpendResponseDTO;
import com.example.DailySpend.model.Spend;

import java.util.List;

public interface SpendService {

  SpendResponseDTO addSpend(String email, SpendRequestDTO spendRequestDTO);
  List<SpendDailyResponseDTO> getSpendDaily(String email, SpendDailyRequestDTO spendDailyRequestDTO);



    default SpendResponseDTO spendToDTO(Spend spend){

        String msg =
                "Successfully added " +
                        spend.getName() +
                        " with amount " +
                        spend.getAmount() +
                        " and categor: " +
                        spend.getCategory();

        return new SpendResponseDTO(
                msg
        );
    }

    default SpendDailyResponseDTO spendDailyToDTO(Spend spend){

        String msg =
                "Spend for: " +
                        spend.getName() +
                        " costs: " +
                        spend.getAmount();

        return new SpendDailyResponseDTO(
                msg
        );
    }
}
