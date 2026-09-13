package com.example.DailySpend.service;

import com.example.DailySpend.dto.AccountRequestDTO;
import com.example.DailySpend.dto.AccountResponseDTO;
import com.example.DailySpend.exceptions.AccountExistsException;
import com.example.DailySpend.model.Account;
import com.example.DailySpend.repository.AccountRepository;
import com.example.DailySpend.service.declarations.AccountService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(
            AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountResponseDTO register(AccountRequestDTO accountRequestDTO){
        boolean accountExists = accountRepository.existsByEmail(accountRequestDTO.email());

        if (accountExists) {
            throw new AccountExistsException("Account already exists by email: " + accountRequestDTO.email());
        }

        Date createdAt = new Date();

        Account account = new Account(
                null,
                accountRequestDTO.firstName(),
                accountRequestDTO.lastName(),
                accountRequestDTO.email(),
                accountRequestDTO.age(),
                accountRequestDTO.pwd(),
                createdAt
        );

        return toDTO(accountRepository.save(account));
    }

}
