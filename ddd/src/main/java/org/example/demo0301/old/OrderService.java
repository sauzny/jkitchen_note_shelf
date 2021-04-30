package org.example.demo0301.old;

public class OrderService {

    private OrderDAO orderDAO;

    private Cache cache;

    public Long addOrder(RequestDTO request) {
        // 此处省略很多拼装逻辑
        OrderDO orderDO = new OrderDO();
        orderDAO.insertOrder(orderDO);
        cache.put(orderDO.getId(), orderDO);
        return orderDO.getId();
    }

    public void updateOrder(OrderDO orderDO, RequestDTO updateRequest) {
        orderDO.setXXX("XXX"); // 省略很多
        orderDAO.updateOrder(orderDO);
        cache.put(orderDO.getId(), orderDO);
    }

    public void doSomeBusiness(Long id) {

        OrderDO orderDO = cache.get(id);
        if (orderDO == null) {
            orderDO = orderDAO.getOrderById(id);
        }
        // 此处省略很多业务逻辑
    }
}
