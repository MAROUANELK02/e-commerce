package com.ecom.inventory_service.kafka;

import com.ecom.inventory_service.dtos.OrderCreated;
import com.ecom.inventory_service.dtos.OrderItemDTOResponse;
import com.ecom.inventory_service.exceptions.InsufficientStockException;
import com.ecom.inventory_service.exceptions.InventoryNotFoundException;
import com.ecom.inventory_service.services.InventoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class InventoryConsumer {
    private final InventoryService inventoryService;
    private final KafkaProducer kafkaProducer;

    @KafkaListener(topics = "${topic}", groupId = "${group-id}")
    public void consumeOrderCreated(OrderCreated order) {
        List<OrderItemDTOResponse> orderItems = order.getOrderItems();
        boolean success = true;
        try {
            for (OrderItemDTOResponse orderItem : orderItems) {
                if(!inventoryService.checkStockAvailability(orderItem.getProductId(), orderItem.getQuantity())) {
                    success = false;
                    break;
                }
            }
        } catch (InventoryNotFoundException e) {
            throw new RuntimeException(e);
        }
        if(success) {
            for (OrderItemDTOResponse orderItem : orderItems) {
                try {
                    inventoryService.reduceStock(orderItem.getProductId(), orderItem.getQuantity());
                } catch (InventoryNotFoundException | InsufficientStockException e) {
                    throw new RuntimeException(e);
                }
            }
            kafkaProducer.send("SUCCESS order "+order.getOrderId());
        }else{
            kafkaProducer.send("FAILED order " + order.getOrderId());
        }
    }
}
