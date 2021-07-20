package org.example.demo0501.ddd.application.facead.external;

import org.example.demo0501.ddd.infrastructure.data.ItemDO;

public interface ExternalItemService {

    ItemDO getItem(Long itemId);
}
