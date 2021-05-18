package org.example.demo0301.ddd.application.assembler;

import org.example.demo0301.ddd.application.dto.ItemDTO;
import org.example.demo0301.ddd.application.dto.OrderDTO;
import org.example.demo0301.ddd.domain.entity.Item;
import org.example.demo0301.ddd.domain.entity.Order;

public class DtoAssemblerOld {
    // 通过各种实体，生成DTO
    public OrderDTO toDTO(Order order, Item item) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId().getValue());
        dto.setItemTitle(item.getTitle()); // 从多个对象里取值，且字段名称不一样
        dto.setDetailAddress(order.getAddress().getDetail()); // 可以读取复杂嵌套字段
        // 省略N行
        return dto;
    }

    // 通过DTO，生成实体
    public Item toEntity(ItemDTO itemDTO) {
        Item entity = new Item();
        entity.setId(itemDTO.getId());
        // 省略N行
        return entity;
    }
}
