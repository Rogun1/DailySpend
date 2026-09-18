package com.example.DailySpend.service.declarations;

import com.example.DailySpend.dto.AddIncomeRequestDTO;
import com.example.DailySpend.dto.AddIncomeResponseDTO;
import com.example.DailySpend.model.Income;

public interface IncomeService {

    AddIncomeResponseDTO addIncome(String email, AddIncomeRequestDTO addIncomeRequestDTO);

    default AddIncomeResponseDTO incomeToDTO(Income income){

        String msg =
                "Successfully added " +
                        income.getName() +
                        " with amount " +
                        income.getAmount();

        return new AddIncomeResponseDTO(
            msg
        );
    };
}
