package com.example.DailySpend.service.declarations;

import com.example.DailySpend.dto.SpendRequestDTO;
import com.example.DailySpend.dto.SpendResponseDTO;
import com.example.DailySpend.model.Spend;

public interface SpendService {

  SpendResponseDTO addSpend(String email, SpendRequestDTO spendRequestDTO);

    default SpendResponseDTO spendToDTO(Spend spend){

        String msg =
                "Successfully added " +
                        spend.getName() +
                        " with amount " +
                        spend.getAmount();

        return new SpendResponseDTO(
                msg
        );
    }
}
