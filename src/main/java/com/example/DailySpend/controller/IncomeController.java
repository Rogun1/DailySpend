package com.example.DailySpend.controller;

import com.example.DailySpend.dto.AddIncomeRequestDTO;
import com.example.DailySpend.dto.AddIncomeResponseDTO;
import com.example.DailySpend.service.declarations.IncomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts/income")
@RequiredArgsConstructor
public class IncomeController {

    private final IncomeService incomeService;

    @PostMapping("/add")
    public AddIncomeResponseDTO addIncome(Authentication authentication, @RequestBody AddIncomeRequestDTO addIncomeRequestDTO){
        return incomeService.addIncome(authentication.getName(), addIncomeRequestDTO);
    }
}
