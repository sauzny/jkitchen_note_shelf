package org.example.domain.service;

import org.example.domain.entity.Account;
import org.example.exception.DailyLimitExceededException;
import org.example.exception.InsufficientFundsException;
import org.example.exception.InvalidCurrencyException;
import org.example.types.ExchangeRate;
import org.example.types.Money;

public interface AccountTransferService {
    void transfer(Account sourceAccount, Account targetAccount, Money targetMoney, ExchangeRate exchangeRate) throws InvalidCurrencyException, InsufficientFundsException, DailyLimitExceededException;
}
