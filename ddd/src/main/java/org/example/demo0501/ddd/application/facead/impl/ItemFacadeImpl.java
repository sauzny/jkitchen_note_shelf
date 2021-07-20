package org.example.demo0501.ddd.application.facead.impl;

import org.example.demo0501.ddd.application.dto.ItemDTO;
import org.example.demo0501.ddd.application.facead.ItemFacade;
import org.example.demo0501.ddd.application.facead.external.ExternalItemService;
import org.example.demo0501.ddd.infrastructure.data.ItemDO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ItemFacadeImpl implements ItemFacade {

    @Resource
    private ExternalItemService externalItemService;

    @Override
    public ItemDTO getItem(Long itemId) {
        ItemDO itemDO = externalItemService.getItem(itemId);
        if (itemDO != null) {
            ItemDTO dto = new ItemDTO();
            dto.setItemId(itemDO.getItemId());
            dto.setTitle(itemDO.getTitle());
            dto.setPriceInCents(itemDO.getPriceInCents());
            dto.setSellerId(itemDO.getSellerId());
            return dto;
        }
        return null;
    }
}
