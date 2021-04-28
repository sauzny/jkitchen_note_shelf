package org.example.persistence;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountDO {

    private Long id;
    private String currency;
    private BigDecimal available;
    private BigDecimal dailyLimit;
}
