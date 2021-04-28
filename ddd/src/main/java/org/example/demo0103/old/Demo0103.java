package org.example.demo0103.old;

import java.math.BigDecimal;

public class Demo0103 {

    /**
     * 假设用户可能要做跨境转账从 CNY 到 USD ，并且货币汇率随时在波动
     */

    public void pay(Money money, Currency targetCurrency, Long recipientId) {

        // ** 问题 金额的计算被包含在了支付的服务中
        // ** 涉及到的对象也有2个 Currency ，2 个 Money ，1 个 BigDecimal ，总共 5 个对象。
        // ** 这种涉及到多个对象的业务逻辑，需要用 DP 包装掉

        if (money.getCurrency().equals(targetCurrency)) {
            BankService.transfer(money, recipientId);
        } else {
            BigDecimal rate = ExchangeService.getRate(money.getCurrency(), targetCurrency);
            BigDecimal targetAmount = money.getAmount().multiply(rate);
            Money targetMoney = new Money(targetAmount, targetCurrency);
            BankService.transfer(targetMoney, recipientId);
        }
    }
}
