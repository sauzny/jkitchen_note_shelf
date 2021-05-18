package org.example.demo0301.ddd.infrastructure.converter;

import org.example.demo0301.ddd.domain.entity.Order;
import org.example.demo0301.ddd.infrastructure.dao.OrderDO;

public class OrderDataConverter {

    public static final OrderDataConverter INSTANCE = new OrderDataConverter();

    public Order fromData(OrderDO orderDO){
        return new Order();
    }
    public OrderDO toData(Order order){
        return new OrderDO();
    }
}
