package org.example.demo0301.ddd.domain.entity;

import lombok.Data;

@Data
public class Order {

    private Long id;
    private Address address;
    private Long itemId;
}
