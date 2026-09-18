package com.example.DailySpend.model;

import com.example.DailySpend.constants.IncomeType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "incomes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Income {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private IncomeType incomeType;
    private Date date;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Income income = (Income) o;
        return Objects.equals(id, income.id) && Objects.equals(name, income.name) && Objects.equals(amount, income.amount) && incomeType == income.incomeType && Objects.equals(date, income.date) && Objects.equals(account, income.account);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, amount, incomeType, date, account);
    }
}
