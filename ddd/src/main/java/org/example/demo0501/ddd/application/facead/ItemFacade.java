package org.example.demo0501.ddd.application.facead;

import org.example.demo0501.ddd.application.dto.ItemDTO;

public interface ItemFacade {
    ItemDTO getItem(Long itemId);
}
