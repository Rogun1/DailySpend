package com.example.DailySpend.service;

import com.example.DailySpend.dto.SpendRequestDTO;
import com.example.DailySpend.dto.SpendResponseDTO;
import com.example.DailySpend.exceptions.AccountNotFoundException;
import com.example.DailySpend.model.Account;
import com.example.DailySpend.model.Spend;
import com.example.DailySpend.repository.AccountRepository;
import com.example.DailySpend.repository.SpendRepository;
import com.example.DailySpend.service.declarations.SpendService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SpendServiceImpl implements SpendService {

    private final SpendRepository spendRepository;
    private final AccountRepository accountRepository;

    public SpendServiceImpl(
            SpendRepository spendRepository,
            AccountRepository accountRepository
    ){
        this.spendRepository = spendRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public SpendResponseDTO addSpend(String email, SpendRequestDTO spendRequestDTO){
        Account account = accountRepository.findByEmail(email)
                .orElseThrow( () -> new AccountNotFoundException("Account not found for email: " + email));

        Spend spend = new Spend(
                null,
                spendRequestDTO.name(),
                spendRequestDTO.amount(),
                new Date(),
                account
        );

        spendRepository.save(spend);

        return spendToDTO(spend);
    }
}
