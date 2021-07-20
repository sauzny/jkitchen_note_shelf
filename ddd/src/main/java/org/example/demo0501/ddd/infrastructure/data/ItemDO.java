package org.example.demo0501.ddd.infrastructure.data;

import lombok.Data;

@Data
public class ItemDO {
    private long sellerId;
    private long priceInCents;
    private Long itemId;
    private String title;
}
