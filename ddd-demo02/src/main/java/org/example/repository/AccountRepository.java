package org.example.repository;

import org.example.domain.entity.Account;
import org.example.types.AccountId;
import org.example.types.AccountNumber;
import org.example.types.UserId;

public interface AccountRepository {
    Account find(AccountId id);
    Account find(AccountNumber accountNumber);
    Account find(UserId userId);
    Account save(Account account);
}
