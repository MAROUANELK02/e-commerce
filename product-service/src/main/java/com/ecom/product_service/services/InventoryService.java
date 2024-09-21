package com.ecom.product_service.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "inventory-service")
public interface InventoryService {

    @RequestMapping(method = RequestMethod.POST, value = "/createInventory/{productId}")
    void createInventory(@PathVariable("productId") long productId);

}
