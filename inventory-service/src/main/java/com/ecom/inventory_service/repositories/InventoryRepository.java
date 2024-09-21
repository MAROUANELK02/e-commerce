package com.ecom.inventory_service.repositories;

import com.ecom.inventory_service.entities.Inventory;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface InventoryRepository extends CrudRepository<Inventory, Long> {
    Optional<Inventory> findByProductId(long id);
}
