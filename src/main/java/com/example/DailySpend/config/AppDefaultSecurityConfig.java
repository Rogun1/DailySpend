package com.example.DailySpend.config;

import com.example.DailySpend.exceptions.CustomAccesDeniedHandler;
import com.example.DailySpend.exceptions.CustomBasicAuthenticationEntryPoint;
import com.example.DailySpend.filters.JWTTokenGeneratorFilter;
import com.example.DailySpend.filters.JWTTokenValidatorFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;


@Configuration
@Profile("default")
@EnableWebSecurity
@EnableMethodSecurity
public class AppDefaultSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) {

        http
                .sessionManagement(smc -> smc
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .redirectToHttps((https) -> https.requestMatchers(AnyRequestMatcher.INSTANCE))
                .cors(cors -> {})
                .csrf(csrfConfig -> csrfConfig.disable()
                .addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)
                .addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/*/income").hasAnyRole("MEMBER","ADMIN")
                        .requestMatchers("/*/income/add").hasAnyRole("MEMBER","ADMIN")
                        .requestMatchers("/*/spend").hasAnyRole("MEMBER","ADMIN")
                        .requestMatchers("/*/spend/add").hasAnyRole("MEMBER","ADMIN")
                        .requestMatchers("/*/spend/daily").hasAnyRole("MEMBER","ADMIN")
                        .requestMatchers("/*/spend/summary/**").hasAnyRole("MEMBER","ADMIN")
                        .requestMatchers("/*/spend/categories").hasAnyRole("MEMBER","ADMIN")
                        .requestMatchers("/accounts/**").hasAnyRole("MEMBER","ADMIN")
                        .requestMatchers("/login").authenticated()
                        .requestMatchers("/contact", "/error", "/accounts").permitAll()));
        http.httpBasic(hbc -> hbc.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));
        http.exceptionHandling(ehc -> ehc.accessDeniedHandler(new CustomAccesDeniedHandler()));
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        configuration.setExposedHeaders(List.of("Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public CompromisedPasswordChecker compromisedPasswordChecker(){
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }
}
