package org.example.demo0103.ddd;

import java.math.BigDecimal;

public class ExchangeService {

    public static ExchangeRate getRate(Currency fromCurrency, Currency targetCurrency) {
        if (fromCurrency.equals(targetCurrency)) {
            return new ExchangeRate(new BigDecimal(1), fromCurrency, targetCurrency);
        } else {
            // 按照实时浮动计算汇率
            // 此处省略 计算汇率 过程，固定返回 1.2
            return new ExchangeRate(new BigDecimal("1.2"), fromCurrency, targetCurrency);
        }
    }
}
