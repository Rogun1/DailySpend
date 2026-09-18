package com.example.DailySpend.service;

import com.example.DailySpend.constants.SpendCategory;
import com.example.DailySpend.dto.*;
import com.example.DailySpend.exceptions.AccountNotFoundException;
import com.example.DailySpend.model.Account;
import com.example.DailySpend.model.Spend;
import com.example.DailySpend.repository.AccountRepository;
import com.example.DailySpend.repository.SpendRepository;
import com.example.DailySpend.service.declarations.SpendService;
import jdk.jfr.Category;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

        String categoryUpperCase = spendRequestDTO.category().toUpperCase();
        System.out.println("category to upper " + categoryUpperCase);
        SpendCategory categoryFound = null;

        for (SpendCategory category: SpendCategory.values()){
            if (category.name().equals(categoryUpperCase)){
                categoryFound = category;
                System.out.println("category found" + categoryFound);
            }
        }

        if (categoryFound == null){
            throw  new RuntimeException("Category not found");
        }

        Spend spend = new Spend(
                null,
                spendRequestDTO.name(),
                spendRequestDTO.amount(),
                spendRequestDTO.quantity(),
                categoryFound,
                LocalDate.now(),
                account
        );

        System.out.println(spend.getCategory());

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

    @Override
    public SummaryResponseDTO summary(String email, Long lastDays){

        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new AccountNotFoundException("Account not found for email: " + email));

        LocalDate dateTo = LocalDate.now();
        LocalDate dateFrom = dateTo.minusDays(lastDays);

        List<Spend> spendList = spendRepository.findAllByAccountIdAndSpendDateBetween(account.getId(), dateFrom, dateTo);

        Map<SpendCategory, Double> amountPerCategory = spendList.stream()
                .collect(Collectors.groupingBy(Spend::getCategory,
                        Collectors.summingDouble(Spend::getAmount)));

        Double total = amountPerCategory.values()
                .stream()
                .mapToDouble(Double::doubleValue)
                .sum();

        return summaryToDTO(amountPerCategory, total);
    }
}
