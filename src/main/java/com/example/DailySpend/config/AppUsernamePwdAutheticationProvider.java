package com.example.DailySpend.config;

import com.example.DailySpend.service.AppUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile("!default")
@RequiredArgsConstructor
public class AppUsernamePwdAutheticationProvider implements AuthenticationProvider {

    private final AppUserDetailsService appUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public @Nullable Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String pwd = authentication.getCredentials().toString();
        UserDetails userDetails = appUserDetailsService.loadUserByUsername(username);

        if (passwordEncoder.matches(pwd, userDetails.getPassword())){
            return new UsernamePasswordAuthenticationToken(
                    username,
                    pwd,
                    userDetails.getAuthorities());
        }else {
            throw new BadCredentialsException("Invalid password");
        }

    }

    @Override
    public boolean supports(Class<?> authentication) {
        //Dao implementation of support method
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
