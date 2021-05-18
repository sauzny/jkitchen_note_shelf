package org.example.demo0301.ddd.infrastructure.impl;

import org.example.demo0301.ddd.domain.acl.cqe.OrderQuery;
import org.example.demo0301.ddd.domain.acl.repository.OrderRepository;
import org.example.demo0301.ddd.domain.entity.LineItem;
import org.example.demo0301.ddd.domain.entity.Order;
import org.example.demo0301.ddd.infrastructure.converter.LineItemDataConverter;
import org.example.demo0301.ddd.infrastructure.converter.OrderDataConverter;
import org.example.demo0301.ddd.infrastructure.dao.LineItemDAO;
import org.example.demo0301.ddd.infrastructure.dao.LineItemDO;
import org.example.demo0301.ddd.infrastructure.dao.OrderDAO;
import org.example.demo0301.ddd.infrastructure.dao.snapshot.*;
import org.example.demo0301.ddd.types.OrderId;
import org.example.demo0301.ddd.types.Page;
import org.example.demo0301.ddd.types.StoreId;
import org.example.demo0301.ddd.infrastructure.dao.OrderDO;

import java.util.List;
import java.util.stream.Collectors;

// 代码在Infrastructure层
// @Repository // Spring的注解
public class OrderRepositoryImpl extends DbRepositorySupport<Order, OrderId> implements OrderRepository {
    private final OrderDAO orderDAO; // 具体的DAO接口
    private final OrderDataConverter converter; // 转化器

    private LineItemDAO lineItemDAO;
    private LineItemDataConverter lineItemConverter;

    public OrderRepositoryImpl(Class<Order> targetClass, OrderDAO orderDAO) {
        super(targetClass);
        this.orderDAO = orderDAO;
        this.converter = OrderDataConverter.INSTANCE;
    }

    @Override
    public void attach(Order aggregate) {

    }

    @Override
    public void detach(Order aggregate) {

    }

    @Override
    public Order find(OrderId orderId) {
        OrderDO orderDO = orderDAO.findById(orderId.getValue());
        return converter.fromData(orderDO);
    }

    @Override
    public void remove(Order aggregate) {
        OrderDO orderDO = converter.toData(aggregate);
        orderDAO.delete(orderDO);
    }

    @Override
    public void save(Order aggregate) {
        if (aggregate.getId() != null && aggregate.getId().getValue() > 0) {
            // update
            OrderDO orderDO = converter.toData(aggregate);
            orderDAO.update(orderDO);
        } else {
            // insert
            OrderDO orderDO = converter.toData(aggregate);
            orderDAO.insert(orderDO);
            aggregate.setId(converter.fromData(orderDO).getId());
        }
    }

    @Override
    public Long count(OrderQuery query) {
        return null;
    }

    @Override
    public Page<Order> query(OrderQuery query) {
        List<OrderDO> orderDOS = orderDAO.queryPaged(query);
        long count = orderDAO.count(query);
        List<Order> result = orderDOS.stream().map(converter::fromData).collect(Collectors.toList());
        return Page.with(result, query, count);
    }

    @Override
    public Order findInStore(OrderId id, StoreId storeId) {
        OrderDO orderDO = orderDAO.findInStore(id.getValue(), storeId.getValue());
        return converter.fromData(orderDO);
    }

    @Override
    protected void onInsert(Order aggregate) {

    }

    @Override
    protected Order onSelect(OrderId orderId) {
        return null;
    }

    @Override
    protected void onUpdate(Order aggregate, EntityDiff diff) {
        if (diff.isSelfModified()) {
            OrderDO orderDO = converter.toData(aggregate);
            orderDAO.update(orderDO);
        }

        Diff lineItemDiffs = diff.getDiff("lineItems");
        if (lineItemDiffs instanceof ListDiff) {
            ListDiff diffList = (ListDiff) lineItemDiffs;
            for (Diff itemDiff : diffList) {
                if (itemDiff.getType() == DiffType.Removed) {
                    LineItem line = (LineItem) itemDiff.getOldValue();
                    LineItemDO lineDO = lineItemConverter.toData(line);
                    lineItemDAO.delete(lineDO);
                }
                if (itemDiff.getType() == DiffType.Added) {
                    LineItem line = (LineItem) itemDiff.getNewValue();
                    LineItemDO lineDO = lineItemConverter.toData(line);
                    lineItemDAO.insert(lineDO);
                }
                if (itemDiff.getType() == DiffType.Modified) {
                    LineItem line = (LineItem) itemDiff.getNewValue();
                    LineItemDO lineDO = lineItemConverter.toData(line);
                    lineItemDAO.update(lineDO);
                }
            }
        }
    }

    @Override
    protected void onDelete(Order aggregate) {

    }
}
