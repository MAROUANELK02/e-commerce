package com.ecom.payment_service.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("user-service")
public interface UserService {

    @GetMapping("/user/mail/{id}")
    String getUserMail(@PathVariable("id") long id);
}