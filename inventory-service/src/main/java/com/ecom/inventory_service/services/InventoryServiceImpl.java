package com.ecom.inventory_service.services;

import com.ecom.inventory_service.entities.Inventory;
import com.ecom.inventory_service.exceptions.InsufficientStockException;
import com.ecom.inventory_service.exceptions.InventoryAlreadyExistsException;
import com.ecom.inventory_service.exceptions.InventoryNotFoundException;
import com.ecom.inventory_service.repositories.InventoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository inventoryRepository;

    @Override
    public Inventory getInventoryByProductId(long productId) throws InventoryNotFoundException {
        Optional<Inventory> byProductId = inventoryRepository.findByProductId(productId);
        if(byProductId.isPresent()) {
            return byProductId.get();
        }else{
            throw new InventoryNotFoundException("Inventory not found");
        }
    }

    @Override
    public int getProductQuantityByProductId(long productId) throws InventoryNotFoundException {
        return getInventoryByProductId(productId).getQuantity();
    }

    @Override
    public boolean checkStockAvailability(long productId, int quantity) throws InventoryNotFoundException {
        return getProductQuantityByProductId(productId) >= quantity;
    }

    @Override
    public String addStock(long productId, int quantity) throws InventoryNotFoundException {
        Inventory inventory = getInventoryByProductId(productId);
        inventory.setQuantity(inventory.getQuantity() + quantity);
        inventoryRepository.save(inventory);
        return "Stock added successfully";
    }

    @Override
    public String reduceStock(long productId, int quantity) throws InventoryNotFoundException, InsufficientStockException {
        Inventory inventory = getInventoryByProductId(productId);
        if(inventory.getQuantity() > 0 && inventory.getQuantity() >= quantity) {
            inventory.setQuantity(inventory.getQuantity() - quantity);
            inventoryRepository.save(inventory);
            return "Stock reduced successfully";
        }else{
            throw new InsufficientStockException("Insufficient stock");
        }
    }

    @Override
    public Inventory createInventory(long productId) throws InventoryAlreadyExistsException {
        if(inventoryRepository.findByProductId(productId).isPresent()) {
            throw new InventoryAlreadyExistsException("Inventory already exists");
        }else{
            long index = inventoryRepository.count()+1;
            Inventory inventory = new Inventory(index, productId, 0);
            return inventoryRepository.save(inventory);
        }
    }

    @Override
    public String deleteInventory(long productId) throws InventoryNotFoundException {
        Inventory inventory = getInventoryByProductId(productId);
        inventoryRepository.delete(inventory);
        return "Inventory deleted successfully";
    }
}
