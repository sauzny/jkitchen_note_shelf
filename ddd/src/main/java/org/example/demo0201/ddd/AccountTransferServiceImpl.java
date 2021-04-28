package org.example.demo0201.ddd;

public class AccountTransferServiceImpl implements AccountTransferService {

    private ExchangeRateService exchangeRateService;

    @Override
    public void transfer(Account sourceAccount, Account targetAccount, Money targetMoney, ExchangeRate exchangeRate) throws InvalidCurrencyException, InsufficientFundsException, DailyLimitExceededException {
        Money sourceMoney = exchangeRate.exchangeTo(targetMoney);
        sourceAccount.deposit(sourceMoney);
        targetAccount.withdraw(targetMoney);
    }
}
