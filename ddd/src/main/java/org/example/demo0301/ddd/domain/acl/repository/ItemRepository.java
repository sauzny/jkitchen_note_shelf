package org.example.demo0301.ddd.domain.acl.repository;

import org.example.demo0301.ddd.domain.entity.Item;

public interface ItemRepository {
    Item find(Long itemId);
}
