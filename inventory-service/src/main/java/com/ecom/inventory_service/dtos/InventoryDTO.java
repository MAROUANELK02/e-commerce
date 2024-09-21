package com.ecom.inventory_service.dtos;

import lombok.Data;

@Data
public class InventoryDTO {
    private Long inventoryId;
    private Long productId;
    private int quantity;
}
