package com.example.DailySpend.service.declarations;

import com.example.DailySpend.dto.AccountProfileResponseDTO;
import com.example.DailySpend.dto.AccountRequestDTO;
import com.example.DailySpend.dto.AccountResponseDTO;
import com.example.DailySpend.dto.AccountUpdateRequestDTO;
import com.example.DailySpend.model.Account;

public interface AccountService {

    AccountResponseDTO register(AccountRequestDTO accountRequestDTO);
    AccountProfileResponseDTO profile(String email);
    AccountProfileResponseDTO updateProfile(String email, AccountUpdateRequestDTO accountUpdateRequestDTO);

    default AccountResponseDTO toDTO(Account account){
        String message = "Account " + account.getFirstName() + " created with success.";
        return new AccountResponseDTO(message);
    }

    default AccountProfileResponseDTO accProfileToDTO(Account account){
        return  new AccountProfileResponseDTO(
                account.getFirstName(),
                account.getLastName(),
                account.getAge()
        );
    }
}
