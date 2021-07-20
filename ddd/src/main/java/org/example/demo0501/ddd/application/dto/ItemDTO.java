package org.example.demo0501.ddd.application.dto;

import lombok.Data;

@Data
public class ItemDTO {
    private Long itemId;
    private Long sellerId;
    private String title;
    private Long priceInCents;
}
