package org.example.demo0201.ddd;

public class AccountBuilder {

    public Account toAccount(AccountDO accountDO){
        return new Account();
    }

    public AccountDO fromAccount(Account account){
        return new AccountDO();
    }
}
