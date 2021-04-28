package org.example.domain.types;

import org.example.domain.entity.Account;
import org.example.types.AccountNumber;
import org.example.types.Money;
import org.example.types.UserId;

import java.util.Date;

public class AuditMessage {

    private UserId userId;
    private AccountNumber source;
    private AccountNumber target;
    private Money money;
    private Date date;

    public AuditMessage(Account source, Account target, Money money){

    }

    public String serialize() {
        return userId + "," + source + "," + target + "," + money + "," + date;
    }

    public static AuditMessage deserialize(String value) {
        // todo
        return null;
    }
}
