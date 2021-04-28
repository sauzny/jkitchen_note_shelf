package org.example.persistence;

import org.example.domain.entity.Account;

public class AccountBuilder {

    public Account toAccount(AccountDO accountDO){
        return new Account();
    }

    public AccountDO fromAccount(Account account){
        return new AccountDO();
    }
}
