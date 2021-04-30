package org.example.demo0301.ddd.application;

import org.example.demo0301.ddd.domain.acl.repository.ItemRepository;
import org.example.demo0301.ddd.domain.acl.repository.OrderRepository;
import org.example.demo0301.ddd.domain.entity.Item;
import org.example.demo0301.ddd.domain.entity.Order;

public class Application {
    private DtoAssembler assembler;
    private OrderRepository orderRepository;
    private ItemRepository itemRepository;

    public OrderDTO getOrderDetail(Long orderId) {
        Order order = orderRepository.find(orderId);
        Item item = itemRepository.find(order.getItemId());
        return assembler.toDTO(order, item); // 原来的很多复杂转化逻辑都收敛到一行代码了
    }
}
