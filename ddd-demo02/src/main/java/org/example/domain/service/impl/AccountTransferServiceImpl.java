package org.example.domain.service.impl;

import org.example.domain.entity.Account;
import org.example.domain.service.AccountTransferService;
import org.example.exception.DailyLimitExceededException;
import org.example.exception.InsufficientFundsException;
import org.example.exception.InvalidCurrencyException;
import org.example.external.ExchangeRateService;
import org.example.types.ExchangeRate;
import org.example.types.Money;

public class AccountTransferServiceImpl implements AccountTransferService {

    private ExchangeRateService exchangeRateService;

    @Override
    public void transfer(Account sourceAccount, Account targetAccount, Money targetMoney, ExchangeRate exchangeRate) throws InvalidCurrencyException, InsufficientFundsException, DailyLimitExceededException {
        Money sourceMoney = exchangeRate.exchangeTo(targetMoney);
        sourceAccount.deposit(sourceMoney);
        targetAccount.withdraw(targetMoney);
    }
}
