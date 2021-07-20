package org.example.demo0501.ddd.infrastructure;

import org.example.demo0501.ddd.domain.Order;

public interface OrderRepository {
    Order save(Order order);
}
