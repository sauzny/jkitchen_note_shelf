package org.example.demo0501.ddd.application;

import org.example.demo0501.ddd.application.dto.OrderDTO;
import org.example.demo0501.ddd.controller.cqe.CheckoutCommand;
import org.example.demo0501.ddd.controller.cqe.UpdateOrderCommand;

import javax.validation.Valid;

public interface CheckoutService {
    OrderDTO checkout(@Valid CheckoutCommand cmd);
    OrderDTO updateOrder(@Valid UpdateOrderCommand cmd);
}
