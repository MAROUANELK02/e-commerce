package com.ecom.inventory_service.exceptions;

public class InventoryNotFoundException extends Throwable {
    public InventoryNotFoundException(String inventoryNotFound) {
        super(inventoryNotFound);
    }
}
