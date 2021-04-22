package org.example.demo0102.old;

import java.math.BigDecimal;

public class Demo0102 {

    /**
     * 让A用户可以支付 x 元给用户 B
     */

    // ** 问题 缺少对【货币类型】的参数化
    // ** 默认认为货币是 【CNY】
    public void pay(BigDecimal money, Long recipientId) {
        BankService.transfer(money, "CNY", recipientId);
    }
}
