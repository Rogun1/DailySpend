package com.example.DailySpend.controller;

import com.example.DailySpend.constants.SpendCategory;
import com.example.DailySpend.dto.*;
import com.example.DailySpend.service.declarations.SpendService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
    public SpendDailyResponseDTO getSpendDaily(
            Authentication authentication,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date
    ) {
        return spendService.getSpendDaily(
                authentication.getName(),
                new SpendDailyRequestDTO(date)
        );
    }

    @GetMapping("/summary/{summaryLastDays}")
    public SummaryResponseDTO summary(Authentication authentication, @PathVariable Long summaryLastDays){
        return spendService.summary(authentication.getName(), summaryLastDays);
    }

    //Needed for automatic dropdown for category enum
    @GetMapping("/categories")
    public SpendCategory[] getCategories() {
        return SpendCategory.values();
    }
}
