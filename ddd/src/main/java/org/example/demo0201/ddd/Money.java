package org.example.demo0201.ddd;

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

    public Money add(Money money){
        return this;
    }

    public Money subtract(Money money){
        return this;
    }

    public int compareTo(Money money){
        return 0;
    }
}
