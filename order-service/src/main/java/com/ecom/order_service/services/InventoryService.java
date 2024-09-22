package com.ecom.order_service.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "inventory-service")
public interface InventoryService {

    @RequestMapping(method = RequestMethod.GET, value = "/checkAvailability/{productId}")
    boolean checkAvailability(@PathVariable("productId") long productId,
                              @RequestParam("quantity") int quantity);
}
