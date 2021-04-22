package org.example.demo0103.ddd;

import java.util.Currency;

public class Demo0103 {

    /**
     * 假设用户可能要做跨境转账从 CNY 到 USD ，并且货币汇率随时在波动
     */

    public void pay(Money money, Currency targetCurrency, Long recipientId) {
        ExchangeRate rate = ExchangeService.getRate(money.getCurrency(), targetCurrency);
        Money targetMoney = rate.exchange(money);
        BankService.transfer(targetMoney, recipientId);
    }
}
