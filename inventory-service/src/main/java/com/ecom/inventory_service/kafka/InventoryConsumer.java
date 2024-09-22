package com.ecom.inventory_service.kafka;

import com.ecom.inventory_service.dtos.OrderCreated;
import com.ecom.inventory_service.exceptions.InsufficientStockException;
import com.ecom.inventory_service.exceptions.InventoryNotFoundException;
import com.ecom.inventory_service.services.InventoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class InventoryConsumer {
    private final InventoryService inventoryService;

    @KafkaListener(topics = "${topic}", groupId = "${group-id}")
    public void consumeOrderCreated(OrderCreated order) {
        order.getProducts().forEach((k,v) -> {
            try {
                inventoryService.reduceStock(k,v);
            } catch (InventoryNotFoundException | InsufficientStockException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
