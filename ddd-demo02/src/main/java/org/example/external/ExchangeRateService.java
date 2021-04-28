package org.example.external;


import org.example.types.Currency;
import org.example.types.ExchangeRate;

public interface ExchangeRateService {
    ExchangeRate getExchangeRate(Currency source, Currency target);
}
