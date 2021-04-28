package org.example.demo0201.ddd;

import java.math.BigDecimal;

public class ExchangeRateServiceImpl implements ExchangeRateService {

    //@Autowired
    private YahooForexService yahooForexService;

    @Override
    public ExchangeRate getExchangeRate(Currency source, Currency target) {
        if (source.equals(target)) {
            return new ExchangeRate(BigDecimal.ONE, source, target);
        }
        BigDecimal forex = yahooForexService.getExchangeRate(source, target);
        return new ExchangeRate(forex, source, target);
    }
}
