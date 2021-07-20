package org.example.demo0501.ddd.domain;

import lombok.Data;

@Data
public class Order {


    private long buyerId;
    private long sellerId;
    private long itemId;
    private String itemTitle;

    private Long itemUnitPrice;
    private Integer count;

    // 把原来一个在ApplicationService的计算迁移到Entity里
    public Long getTotalCost() {
        return itemUnitPrice * count;
    }
}
