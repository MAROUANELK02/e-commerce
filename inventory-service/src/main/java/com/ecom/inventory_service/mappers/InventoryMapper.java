package com.ecom.inventory_service.mappers;

import com.ecom.inventory_service.dtos.InventoryDTO;
import com.ecom.inventory_service.entities.Inventory;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class InventoryMapper {
    private final ObjectMapper objectMapper;

    public Inventory toInventory(InventoryDTO inventoryDTO) {
        return objectMapper.convertValue(inventoryDTO, Inventory.class);
    }

    public InventoryDTO toInventoryDTO(Inventory inventory) {
        return objectMapper.convertValue(inventory, InventoryDTO.class);
    }
}
