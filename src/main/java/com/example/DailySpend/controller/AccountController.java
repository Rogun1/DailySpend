package com.example.DailySpend.controller;

import com.example.DailySpend.dto.AccountRequestDTO;
import com.example.DailySpend.dto.AccountResponseDTO;
import com.example.DailySpend.service.declarations.AccountService;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public AccountResponseDTO register(@RequestBody @Valid AccountRequestDTO accountRequestDTO){
        return accountService.register(accountRequestDTO);
    }

}
