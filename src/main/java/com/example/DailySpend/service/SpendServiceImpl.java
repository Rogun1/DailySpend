package com.example.DailySpend.service;

import com.example.DailySpend.constants.SpendCategory;
import com.example.DailySpend.dto.*;
import com.example.DailySpend.exceptions.AccountNotFoundException;
import com.example.DailySpend.model.Account;
import com.example.DailySpend.model.Income;
import com.example.DailySpend.model.Spend;
import com.example.DailySpend.repository.AccountRepository;
import com.example.DailySpend.repository.IncomeRepository;
import com.example.DailySpend.repository.SpendRepository;
import com.example.DailySpend.service.declarations.SpendService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SpendServiceImpl implements SpendService {

    private final SpendRepository spendRepository;
    private final AccountRepository accountRepository;
    private final IncomeRepository incomeRepository;

    public SpendServiceImpl(
            SpendRepository spendRepository,
            AccountRepository accountRepository,
            IncomeRepository incomeRepository
    ){
        this.spendRepository = spendRepository;
        this.accountRepository = accountRepository;
        this.incomeRepository = incomeRepository;
    }

    @Override
    public SpendResponseDTO addSpend(String email, SpendRequestDTO spendRequestDTO){
        Account account = accountRepository.findByEmail(email)
                .orElseThrow( () -> new AccountNotFoundException("Account not found for email: " + email));

        if (spendRequestDTO.amount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Invalid amount");
        }
        if (spendRequestDTO.quantity() <= 0){
            throw new RuntimeException("Invalid quantity");
        }

        String categoryUpperCase = spendRequestDTO.category().toUpperCase();
        SpendCategory categoryFound = null;

        for (SpendCategory category: SpendCategory.values()){
            if (category.name().equals(categoryUpperCase)){
                categoryFound = category;
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

        spendRepository.save(spend);

        return spendToDTO(spend);
    }

    @Override
    public SpendDailyResponseDTO getSpendDaily(
            String email,
            SpendDailyRequestDTO spendDailyRequestDTO
    ) {
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() ->
                        new AccountNotFoundException(
                                "Account not found for email: " + email
                        )
                );

        LocalDate date = spendDailyRequestDTO.date();

        List<Spend> spendsList =
                spendRepository.findAllByAccountIdAndSpendDate(
                        account.getId(),
                        date
                );

        BigDecimal total = spendsList.stream()
                .map(spend -> spend.getAmount()
                        .multiply(BigDecimal.valueOf(spend.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<SpendDailyItemDTO> spends = spendsList.stream()
                .map(this::spendDailyItemToDTO)
                .toList();

        return new SpendDailyResponseDTO(
                spends,
                total
        );
    }

    @Override
    public SummaryResponseDTO summary(String email, Long lastDays){

        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new AccountNotFoundException("Account not found for email: " + email));

        LocalDate dateTo = LocalDate.now();
        LocalDate dateFrom = dateTo.minusDays(lastDays);

        List<Spend> spendList = spendRepository.findAllByAccountIdAndSpendDateBetween(account.getId(), dateFrom, dateTo);

        Map<SpendCategory, BigDecimal> amountPerCategory = spendList.stream()
                .collect(Collectors.groupingBy(
                        Spend::getCategory,
                        Collectors.reducing(
                                BigDecimal.ZERO,
                                spend -> spend.getAmount()
                                        .multiply(BigDecimal.valueOf(spend.getQuantity())),
                                BigDecimal::add
                        )
                ));

        BigDecimal total = amountPerCategory.values()
                .stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return summaryToDTO(amountPerCategory, total);
    }
}
