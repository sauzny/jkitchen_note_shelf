package org.example.demo0501.ddd.application.facead.external;

public interface ExternalInventoryService {
    boolean withhold(Long itemId, Integer quantity);
}
