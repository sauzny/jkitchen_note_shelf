package org.example.demo0103.ddd;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class Money {
    private BigDecimal amount;
    private Currency currency;
    public Money(BigDecimal amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }
}
