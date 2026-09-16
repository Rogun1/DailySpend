package com.example.DailySpend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "spends")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Spend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double amount;
    private LocalDate spendDate;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

}
