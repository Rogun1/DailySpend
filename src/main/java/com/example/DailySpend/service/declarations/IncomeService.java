package com.example.DailySpend.service.declarations;

import com.example.DailySpend.dto.AddIncomeRequestDTO;
import com.example.DailySpend.dto.AddIncomeResponseDTO;
import com.example.DailySpend.model.Income;

import java.util.HashMap;
import java.util.Map;

public interface IncomeService {

    AddIncomeResponseDTO addIncome(String email, AddIncomeRequestDTO addIncomeRequestDTO);

    default AddIncomeResponseDTO toDTO(Income income){

        String msg =
                "Successfull added " +
                        income.getName() +
                        " with amount " +
                        income.getAmount();

        return new AddIncomeResponseDTO(
            msg
        );
    };
}
