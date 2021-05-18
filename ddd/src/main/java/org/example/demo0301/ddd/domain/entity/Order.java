package org.example.demo0301.ddd.domain.entity;

import lombok.Data;
import org.example.demo0301.ddd.types.maker.Aggregate;
import org.example.demo0301.ddd.types.OrderId;

@Data
public class Order implements Aggregate<OrderId> {

    private OrderId id;
    private Address address;
    private Long itemId;
}
