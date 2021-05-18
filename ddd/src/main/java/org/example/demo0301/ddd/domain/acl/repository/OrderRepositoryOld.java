package org.example.demo0301.ddd.domain.acl.repository;

import org.example.demo0301.ddd.domain.entity.Order;

public interface OrderRepositoryOld {
    Order find(Long orderId);
}
