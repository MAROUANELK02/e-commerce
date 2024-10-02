package com.ecom.payment_service.mappers;

import com.ecom.payment_service.dtos.PaymentCreated;
import com.ecom.payment_service.entities.Payment;
import com.ecom.payment_service.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PaymentMapper {
    private final UserService userService;

    public PaymentCreated toPaymentCreated(Payment payment) {
        PaymentCreated paymentCreated = new PaymentCreated();
        BeanUtils.copyProperties(payment, paymentCreated);
        paymentCreated.setUserAddress(userService.getUserMail(paymentCreated.getUserId()));
        return paymentCreated;
    }
}
