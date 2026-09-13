package com.example.DailySpend.service.declarations;

import com.example.DailySpend.dto.AccountRequestDTO;
import com.example.DailySpend.dto.AccountResponseDTO;
import com.example.DailySpend.model.Account;

public interface AccountService {

    AccountResponseDTO register(AccountRequestDTO accountRequestDTO);

    default AccountResponseDTO toDTO(Account account){
        String message = "Account " + account.getFirstName() + " created with success.";
        return new AccountResponseDTO(message);
    }
}
