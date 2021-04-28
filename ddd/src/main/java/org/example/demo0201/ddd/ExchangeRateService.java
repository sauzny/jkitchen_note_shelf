package org.example.demo0201.ddd;

public interface ExchangeRateService {
    ExchangeRate getExchangeRate(Currency source, Currency target);
}
