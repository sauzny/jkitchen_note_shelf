package org.example.repository.impl;

import org.example.domain.entity.Account;
import org.example.persistence.AccountBuilder;
import org.example.persistence.AccountDO;
import org.example.persistence.AccountMapper;
import org.example.repository.AccountRepository;
import org.example.types.AccountId;
import org.example.types.AccountNumber;
import org.example.types.UserId;

public class AccountRepositoryImpl implements AccountRepository {

    //@Autowired
    private AccountMapper accountDAO;

    //@Autowired
    private AccountBuilder accountBuilder;

    @Override
    public Account find(AccountId id) {
        AccountDO accountDO = accountDAO.selectById(id.getValue());
        return accountBuilder.toAccount(accountDO);
    }

    @Override
    public Account find(AccountNumber accountNumber) {
        AccountDO accountDO = accountDAO.selectByAccountNumber(accountNumber.getValue());
        return accountBuilder.toAccount(accountDO);
    }

    @Override
    public Account find(UserId userId) {
        AccountDO accountDO = accountDAO.selectByUserId(userId.getId());
        return accountBuilder.toAccount(accountDO);
    }

    @Override
    public Account save(Account account) {
        AccountDO accountDO = accountBuilder.fromAccount(account);
        if (accountDO.getId() == null) {
            accountDAO.insert(accountDO);
        } else {
            accountDAO.update(accountDO);
        }
        return accountBuilder.toAccount(accountDO);
    }

}
