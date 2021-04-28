package org.example.demo0201.old;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountDO {

    private String currency;
    private BigDecimal available;
    private BigDecimal dailyLimit;
}
