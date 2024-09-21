package com.ecom.inventory_service.exceptions;

public class InventoryAlreadyExistsException extends Throwable {
    public InventoryAlreadyExistsException(String inventoryAlreadyExists) {
        super(inventoryAlreadyExists);
    }
}
