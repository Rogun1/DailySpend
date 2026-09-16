package com.example.DailySpend.service;

import com.example.DailySpend.dto.SpendDailyRequestDTO;
import com.example.DailySpend.dto.SpendDailyResponseDTO;
import com.example.DailySpend.dto.SpendRequestDTO;
import com.example.DailySpend.dto.SpendResponseDTO;
import com.example.DailySpend.exceptions.AccountNotFoundException;
import com.example.DailySpend.model.Account;
import com.example.DailySpend.model.Spend;
import com.example.DailySpend.repository.AccountRepository;
import com.example.DailySpend.repository.SpendRepository;
import com.example.DailySpend.service.declarations.SpendService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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
                LocalDate.now(),
                account
        );

        spendRepository.save(spend);

        return spendToDTO(spend);
    }

    @Override
    public List<SpendDailyResponseDTO> getSpendDaily(String email, SpendDailyRequestDTO spendDailyRequestDTO){

        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new AccountNotFoundException("Account not found for email: " + email));

        LocalDate date = spendDailyRequestDTO.date();

        List<SpendDailyResponseDTO> spendsList = spendRepository.findAllByAccountIdAndSpendDate(account.getId(), date)
                .stream()
                .map(this::spendDailyToDTO)
                .toList();

        return spendsList;

    }
}
