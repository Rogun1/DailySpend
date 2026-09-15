package com.example.DailySpend.service;

import com.example.DailySpend.exceptions.AccountNotFoundException;
import com.example.DailySpend.model.Account;
import com.example.DailySpend.repository.AccountRepository;
import com.example.DailySpend.service.declarations.LoginService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {

    private final AccountRepository accountRepository;

    LoginServiceImpl(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    @Override
    public String login(String email) {
        boolean account = accountRepository.existsByEmail(email);

        if (!account){
            return null;
        }

        return "Connected with success";
    }
}
