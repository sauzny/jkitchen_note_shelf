package org.example.demo0501.ddd.application.facead.impl;

import org.example.demo0501.ddd.application.facead.InventoryFacade;
import org.example.demo0501.ddd.application.facead.external.ExternalInventoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class InventoryFacadeImpl implements InventoryFacade {

    @Resource
    private ExternalInventoryService externalInventoryService;

    @Override
    public boolean withhold(Long itemId, Integer quantity) {
        return externalInventoryService.withhold(itemId, quantity);
    }
}
