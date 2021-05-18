package org.example.demo0301.ddd.domain.acl.repository;


import org.example.demo0301.ddd.domain.acl.cqe.OrderQuery;
import org.example.demo0301.ddd.domain.entity.Order;
import org.example.demo0301.ddd.infrastructure.Repository;
import org.example.demo0301.ddd.types.OrderId;
import org.example.demo0301.ddd.types.Page;
import org.example.demo0301.ddd.types.StoreId;

// 代码在Domain层
public interface OrderRepository extends Repository<Order, OrderId> {

    // 自定义Count接口，在这里OrderQuery是一个自定义的DTO
    Long count(OrderQuery query);

    // 自定义分页查询接口
    Page<Order> query(OrderQuery query);

    // 自定义有多个条件的查询接口
    Order findInStore(OrderId id, StoreId storeId);
}
