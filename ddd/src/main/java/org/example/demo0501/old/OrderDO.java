package org.example.demo0501.old;

import lombok.Data;

@Data
public class OrderDO {

    private long itemId;
    private long buyerId;
    private long sellerId;
    private int count;
    private long totalCost;

}
