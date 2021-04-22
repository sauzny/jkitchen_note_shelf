package org.example.demo0102.ddd;

public class Demo0102 {

    /**
     * 让A用户可以支付 x 元给用户 B
     */

    // ** 实际上需要的一个入参是支付金额 + 支付货币
    // ** 通过将默认货币这个隐性的上下文概念显性化，并且和金额合并为 Money ，我们可以避免很多当前看不出来，但未来可能会暴雷的bug。
    public void pay(Money money, Long recipientId) {
        BankService.transfer(money, recipientId);
    }
}
