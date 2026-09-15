package com.example.DailySpend.service;

import com.example.DailySpend.constants.AccountRole;
import com.example.DailySpend.dto.AccountRequestDTO;
import com.example.DailySpend.dto.AccountResponseDTO;
import com.example.DailySpend.exceptions.AccountExistsException;
import com.example.DailySpend.model.Account;
import com.example.DailySpend.model.Authority;
import com.example.DailySpend.repository.AccountRepository;
import com.example.DailySpend.repository.AuthorityRepository;
import com.example.DailySpend.service.declarations.AccountService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    public AccountServiceImpl(
            AccountRepository accountRepository,
            AuthorityRepository authorityRepository,
            PasswordEncoder passwordEncoder){
        this.accountRepository = accountRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AccountResponseDTO register(AccountRequestDTO accountRequestDTO){
        boolean accountExists = accountRepository.existsByEmail(accountRequestDTO.email());

        if (accountExists) {
            throw new AccountExistsException("Account already exists by email: " + accountRequestDTO.email());
        }

        String hashPwd = passwordEncoder.encode(accountRequestDTO.pwd());
        Date createdAt = new Date();

        Account account = new Account(
                null,
                accountRequestDTO.firstName(),
                accountRequestDTO.lastName(),
                accountRequestDTO.email(),
                accountRequestDTO.age(),
                hashPwd,
                createdAt,
                null,
                null,
                null
        );

        Set<Authority> authorities = Set.of(new Authority(
                null,
                "ROLE_" + AccountRole.MEMBER,
                account
        ));

        account.setAuthorities(authorities);

        accountRepository.save(account);

        return toDTO(account);
    }

}
