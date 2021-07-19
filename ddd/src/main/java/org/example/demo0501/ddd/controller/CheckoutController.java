package org.example.demo0501.ddd.controller;

import org.example.demo0501.ddd.application.CheckoutService;
import org.example.demo0501.ddd.application.dto.OrderDTO;
import org.example.demo0501.ddd.controller.aop.ResultHandler;
import org.example.demo0501.ddd.controller.cqe.CheckoutCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class CheckoutController {

    @Autowired
    private CheckoutService checkoutService;

    @PostMapping("checkout")
    @ResultHandler
    public Result<OrderDTO> checkout(Long itemId, Integer quantity) {
        CheckoutCommand cmd = new CheckoutCommand();
        OrderDTO orderDTO = checkoutService.checkout(cmd);
        return Result.success(orderDTO);
    }
}
