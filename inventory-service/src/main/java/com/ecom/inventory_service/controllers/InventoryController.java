package com.ecom.inventory_service.controllers;

import com.ecom.inventory_service.exceptions.InsufficientStockException;
import com.ecom.inventory_service.exceptions.InventoryAlreadyExistsException;
import com.ecom.inventory_service.exceptions.InventoryNotFoundException;
import com.ecom.inventory_service.mappers.InventoryMapper;
import com.ecom.inventory_service.services.InventoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;
    private final InventoryMapper inventoryMapper;

    @GetMapping("/quantity/{productId}")
    public ResponseEntity<?> getQuantity(@PathVariable Long productId) {
        try {
            return ResponseEntity.ok().body(inventoryService.getProductQuantityByProductId(productId));
        } catch (InventoryNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/checkAvailability/{productId}")
    public ResponseEntity<?> checkAvailability(@PathVariable(name = "productId") long productId,
                                               @RequestParam(name = "quantity") int quantity) {
        try {
            return ResponseEntity.ok().body(inventoryService
                    .checkStockAvailability(productId,quantity));
        } catch (InventoryNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/addStock/{productId}")
    public ResponseEntity<?> addStock(@PathVariable(name = "productId") long productId,
                                      @RequestParam(name = "quantity") int quantity) {
        try {
            return ResponseEntity.ok().body(inventoryService.addStock(productId,quantity));
        } catch (InventoryNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/reduceStock/{productId}")
    public ResponseEntity<?> reduceStock(@PathVariable(name = "productId") long productId,
                                      @RequestParam(name = "quantity") int quantity) {
        try {
            return ResponseEntity.ok().body(inventoryService.reduceStock(productId,quantity));
        } catch (InventoryNotFoundException | InsufficientStockException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/createInventory/{productId}")
    public ResponseEntity<?> createInventory(@PathVariable(name = "productId") long productId) {
        try {
            return ResponseEntity.ok().body(inventoryMapper.toInventoryDTO(inventoryService.createInventory(productId)));
        } catch (InventoryAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/inventory/{productId}")
    public ResponseEntity<?> deleteInventory(@PathVariable(name = "productId") long productId) {
        try {
            return ResponseEntity.ok().body(inventoryService.deleteInventory(productId));
        } catch (InventoryNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
