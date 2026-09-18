package com.example.DailySpend.service;

import com.example.DailySpend.constants.IncomeType;
import com.example.DailySpend.dto.AddIncomeRequestDTO;
import com.example.DailySpend.dto.AddIncomeResponseDTO;
import com.example.DailySpend.exceptions.AccountNotFoundException;
import com.example.DailySpend.model.Account;
import com.example.DailySpend.model.Income;
import com.example.DailySpend.repository.AccountRepository;
import com.example.DailySpend.repository.IncomeRepository;
import com.example.DailySpend.service.declarations.IncomeService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Service
public class IncomeServiceImpl implements IncomeService {

    private final IncomeRepository incomeRepository;
    private final AccountRepository accountRepository;

    public IncomeServiceImpl(
            IncomeRepository incomeRepository,
            AccountRepository accountRepository
    ) {
        this.incomeRepository = incomeRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public AddIncomeResponseDTO addIncome(
            String email,
            AddIncomeRequestDTO addIncomeRequestDTO
    ) {
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() ->
                        new AccountNotFoundException(
                                "Account not found for email: " + email
                        )
                );

        if (addIncomeRequestDTO.amount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Invalid amount");
        }

        Income income = new Income(
                null,
                addIncomeRequestDTO.name(),
                addIncomeRequestDTO.amount(),
                addIncomeRequestDTO.incomeType(),
                new Date(),
                account
        );

        incomeRepository.save(income);

        return incomeToDTO(income);
    }

    @Override
    public Map<IncomeType, BigDecimal> getIncomeSummary(String email) {

        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() ->
                        new AccountNotFoundException(
                                "Account not found for email: " + email
                        )
                );

        List<Income> incomes =
                incomeRepository.findAllByAccountId(account.getId());

        Map<IncomeType, BigDecimal> summary =
                new EnumMap<>(IncomeType.class);

        for (IncomeType type : IncomeType.values()) {
            summary.put(type, BigDecimal.ZERO);
        }

        for (Income income : incomes) {
            summary.merge(
                    income.getIncomeType(),
                    income.getAmount(),
                    BigDecimal::add
            );
        }

        return summary;
    }
}