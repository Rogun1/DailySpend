package com.example.DailySpend.controller;

import com.example.DailySpend.dto.*;
import com.example.DailySpend.service.declarations.SpendService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts/spend")
@RequiredArgsConstructor
public class SpendController {

    private final SpendService spendService;

    @PostMapping("/add")
    public SpendResponseDTO addSpend (Authentication authentication, @RequestBody SpendRequestDTO spendRequestDTO){
        return spendService.addSpend(authentication.getName(), spendRequestDTO);
    }

    @GetMapping("/daily")
    public List<SpendDailyResponseDTO> getSpendDaily(Authentication authentication, @RequestBody @Valid SpendDailyRequestDTO spendDailyRequestDTO){
        return spendService.getSpendDaily(authentication.getName(), spendDailyRequestDTO);
    }

    @GetMapping("/summary/{summaryLastDays}")
    public SummaryResponseDTO summary(Authentication authentication, @PathVariable Long summaryLastDays){
        return spendService.summary(authentication.getName(), summaryLastDays);
    }
}
