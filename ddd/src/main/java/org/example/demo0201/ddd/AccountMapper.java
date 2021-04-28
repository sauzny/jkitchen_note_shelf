package org.example.demo0201.ddd;

public class AccountMapper {

    public AccountDO selectById(int id){
        return new AccountDO();
    }

    public AccountDO selectByUserId(Long id){
        return new AccountDO();
    }

    public AccountDO selectByAccountNumber(String number){
        return new AccountDO();
    }

    public void insert(AccountDO accountDO){

    }

    public void update(AccountDO accountDO){

    }
}
