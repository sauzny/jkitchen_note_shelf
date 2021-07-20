package org.example.demo0501.ddd.application.facead;

public interface InventoryFacade {
    boolean withhold(Long itemId, Integer quantity);
}
