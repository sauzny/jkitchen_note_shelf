package org.example.demo0201.ddd;

public interface AccountTransferService {
    void transfer(Account sourceAccount, Account targetAccount, Money targetMoney, ExchangeRate exchangeRate) throws InvalidCurrencyException, InsufficientFundsException, DailyLimitExceededException;
}
