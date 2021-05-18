package org.example.demo0301.ddd.infrastructure.dao;

import org.example.demo0301.ddd.domain.acl.cqe.OrderQuery;

import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    public OrderDO findById(long orderId){
        return new OrderDO();
    }

    public List<OrderDO> queryPaged(OrderQuery query){
        return new ArrayList<>();
    }

    public long count(OrderQuery query){
        return 0L;
    }

    public OrderDO findInStore(long orderId, long storeId){
        return new OrderDO();
    }

    public void delete(OrderDO orderDO){

    }

    public void update(OrderDO orderDO){

    }

    public void insert(OrderDO orderDO){

    }
}
