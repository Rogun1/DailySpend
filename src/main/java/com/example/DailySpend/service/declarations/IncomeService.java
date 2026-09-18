package com.example.DailySpend.service.declarations;

import com.example.DailySpend.constants.IncomeType;
import com.example.DailySpend.dto.AddIncomeRequestDTO;
import com.example.DailySpend.dto.AddIncomeResponseDTO;
import com.example.DailySpend.model.Income;

import java.math.BigDecimal;
import java.util.Map;

public interface IncomeService {

    AddIncomeResponseDTO addIncome(String email, AddIncomeRequestDTO addIncomeRequestDTO);
    Map<IncomeType, BigDecimal> getIncomeSummary(String email);

    default AddIncomeResponseDTO incomeToDTO(Income income){

        String msg =
                "Successfully added " +
                        income.getName() +
                        " with amount " +
                        income.getAmount() +
                        ", added as " +
                        income.getIncomeType();

        return new AddIncomeResponseDTO(
            msg
        );
    };
}
