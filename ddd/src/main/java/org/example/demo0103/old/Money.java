package org.example.demo0103.old;

import lombok.Value;

import java.math.BigDecimal;
import java.util.Currency;

@Value
public class Money {
    private BigDecimal amount;
    private Currency currency;
    public Money(BigDecimal amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }
}
