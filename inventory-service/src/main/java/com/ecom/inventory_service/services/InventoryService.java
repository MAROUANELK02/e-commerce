package com.ecom.inventory_service.services;

import com.ecom.inventory_service.entities.Inventory;
import com.ecom.inventory_service.exceptions.InsufficientStockException;
import com.ecom.inventory_service.exceptions.InventoryAlreadyExistsException;
import com.ecom.inventory_service.exceptions.InventoryNotFoundException;

public interface InventoryService {
    Inventory getInventoryByProductId(long productId) throws InventoryNotFoundException;
    int getProductQuantityByProductId(long productId) throws InventoryNotFoundException;
    boolean checkStockAvailability(long productId, int quantity) throws InventoryNotFoundException;
    String addStock(long productId, int quantity) throws InventoryNotFoundException;
    String reduceStock(long productId, int quantity) throws InventoryNotFoundException, InsufficientStockException;
    Inventory createInventory(long productId) throws InventoryAlreadyExistsException;
    String deleteInventory(long productId) throws InventoryNotFoundException;
}
