package org.example.demo0501.ddd.application.impl;

import org.example.demo0501.ddd.application.CheckoutService;
import org.example.demo0501.ddd.application.assembler.OrderDtoAssembler;
import org.example.demo0501.ddd.application.dto.ItemDTO;
import org.example.demo0501.ddd.application.dto.OrderDTO;
import org.example.demo0501.ddd.application.facead.InventoryFacade;
import org.example.demo0501.ddd.application.facead.ItemFacade;
import org.example.demo0501.ddd.controller.cqe.CheckoutCommand;
import org.example.demo0501.ddd.controller.cqe.UpdateOrderCommand;
import org.example.demo0501.ddd.domain.Order;
import org.example.demo0501.ddd.infrastructure.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Service
@Validated // Spring的注解
public class CheckoutServiceImpl implements CheckoutService {

    private final OrderDtoAssembler orderDtoAssembler = OrderDtoAssembler.INSTANCE;

    @Autowired
    private ItemFacade itemFacade;

    @Autowired
    private InventoryFacade inventoryFacade;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public OrderDTO checkout(@Valid CheckoutCommand cmd) { // 这里@Valid是JSR-303/380的注解
        // 如果校验失败会抛异常，在interface层被捕捉
        ItemDTO item = itemFacade.getItem(cmd.getItemId());
        if (item == null) {
            throw new IllegalArgumentException("Item not found");
        }

        boolean withholdSuccess = inventoryFacade.withhold(cmd.getItemId(), cmd.getQuantity());
        if (!withholdSuccess) {
            throw new IllegalArgumentException("Inventory not enough");
        }

        Order order = new Order();
        order.setBuyerId(cmd.getUserId());
        order.setSellerId(item.getSellerId());
        order.setItemId(item.getItemId());
        order.setItemTitle(item.getTitle());
        order.setItemUnitPrice(item.getPriceInCents());
        order.setCount(cmd.getQuantity());

        Order savedOrder = orderRepository.save(order);

        return orderDtoAssembler.orderToDTO(savedOrder);
    }

    @Override
    public OrderDTO updateOrder(@Valid UpdateOrderCommand cmd) {
        return null;
    }
}
