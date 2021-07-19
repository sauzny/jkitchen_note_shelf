package org.example.demo0501.ddd.application.assembler;

import org.example.demo0501.ddd.application.dto.OrderDTO;
import org.example.demo0501.ddd.domain.Order;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderDtoAssembler {
    OrderDtoAssembler INSTANCE = Mappers.getMapper(OrderDtoAssembler.class);
    OrderDTO orderToDTO(Order order);
}
