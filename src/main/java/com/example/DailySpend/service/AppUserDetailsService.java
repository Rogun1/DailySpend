package com.example.DailySpend.service;

import com.example.DailySpend.exceptions.AccountExistsException;
import com.example.DailySpend.model.Account;
import com.example.DailySpend.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    //find by email not username
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new AccountExistsException("Account doesn't exists by email " + email));

        List<GrantedAuthority> authorityList =
                account.getAuthorities().stream()
                .map(authority -> (GrantedAuthority) new SimpleGrantedAuthority(authority.getName()))
                .toList();

        return new User(account.getEmail(), account.getPwd(), authorityList);
    }
}
