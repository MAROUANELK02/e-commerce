package com.ecom.order_service.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.math.BigDecimal;

@FeignClient(name = "product-service")
public interface ProductService {

    @RequestMapping(method = RequestMethod.GET, value = "/price/{productId}")
    BigDecimal findProductPrice(@PathVariable long productId);

}