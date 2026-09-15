package com.example.DailySpend.service;

import com.example.DailySpend.dto.AddIncomeRequestDTO;
import com.example.DailySpend.dto.AddIncomeResponseDTO;
import com.example.DailySpend.exceptions.AccountNotFoundException;
import com.example.DailySpend.model.Account;
import com.example.DailySpend.model.Income;
import com.example.DailySpend.repository.AccountRepository;
import com.example.DailySpend.repository.IncomeRepository;
import com.example.DailySpend.service.declarations.IncomeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class IncomeServiceImpl implements IncomeService {

    private final IncomeRepository incomeRepository;
    private final AccountRepository accountRepository;

    public IncomeServiceImpl(
            IncomeRepository incomeRepository,
            AccountRepository accountRepository
    ){
        this.incomeRepository = incomeRepository;
        this.accountRepository = accountRepository;
    }


    @Override
    public AddIncomeResponseDTO addIncome(String email, AddIncomeRequestDTO addIncomeRequestDTO){
        Account account = accountRepository.findByEmail(email)
                .orElseThrow( () -> new AccountNotFoundException("Account not found for email: " + email));

        Date gotAt = new Date();

        Income income = new Income(
                null,
                addIncomeRequestDTO.name(),
                addIncomeRequestDTO.amount(),
                gotAt,
                account
        );

        incomeRepository.save(income);

        return toDTO(income);
    }
}
