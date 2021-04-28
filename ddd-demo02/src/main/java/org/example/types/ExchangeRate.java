package org.example.types;

import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class ExchangeRate {
    private BigDecimal rate;
    private Currency from;
    private Currency to;

    public ExchangeRate(BigDecimal rate, Currency from, Currency to) {
        this.rate = rate;
        this.from = from;
        this.to = to;
    }

    public Money exchangeTo(@NonNull Money fromMoney) {
        isTrue(this.from.equals(fromMoney.getCurrency()));
        BigDecimal targetAmount = fromMoney.getAmount().multiply(rate);
        return new Money(targetAmount, to);
    }

    private void isTrue(boolean isTrue){
        if(!isTrue){
            throw new RuntimeException("参数不合法");
        }
    }

}
