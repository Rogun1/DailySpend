package com.example.DailySpend.controller;

import com.example.DailySpend.dto.AccountProfileResponseDTO;
import com.example.DailySpend.dto.AccountRequestDTO;
import com.example.DailySpend.dto.AccountResponseDTO;
import com.example.DailySpend.dto.AccountUpdateRequestDTO;
import com.example.DailySpend.service.declarations.AccountService;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public AccountResponseDTO register(@RequestBody @Valid AccountRequestDTO accountRequestDTO){
        return accountService.register(accountRequestDTO);
    }

    @GetMapping("/me")
    public AccountProfileResponseDTO profile(Authentication authentication){
        return accountService.profile(authentication.getName());
    }

    @PutMapping("/me")
    public AccountProfileResponseDTO updateProfile(
            Authentication authentication,
            @RequestBody @Valid AccountUpdateRequestDTO accountUpdateRequestDTO
    ) {
        return accountService.updateProfile(
                authentication.getName(),
                accountUpdateRequestDTO
        );
    }

}
