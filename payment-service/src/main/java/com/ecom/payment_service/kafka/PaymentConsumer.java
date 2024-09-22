package com.ecom.payment_service.kafka;

import com.ecom.payment_service.dtos.OrderCreated;
import com.ecom.payment_service.entities.Payment;
import com.ecom.payment_service.enums.PaymentStatus;
import com.ecom.payment_service.paypal.PaypalService;
import com.ecom.payment_service.repositories.PaymentRepository;
import com.paypal.api.payments.Links;
import com.paypal.base.rest.PayPalRESTException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@AllArgsConstructor
public class PaymentConsumer {
    private final PaypalService paypalService;
    private final PaymentRepository paymentRepository;

    @KafkaListener(topics = "${topic}", groupId = "${group-id}")
    public void consumeOrderCreated(OrderCreated order) {
        log.info("Order created: {}", order);
        Payment payment = Payment.builder()
                .orderId(order.getOrderId())
                .paymentDate(LocalDateTime.now())
                .amount(order.getTotalAmount())
                .status(PaymentStatus.PENDING)
                .userId(order.getUserId())
                .build();

        try {
            com.paypal.api.payments.Payment payment1 = paypalService
                    .createPayment(payment.getAmount(), "USD", "sale",
                            "description of sale");

            for(Links links : payment1.getLinks()) {
                if(links.getRel().equals("approval_url")) {
                    payment.setRedirectUrl(String.valueOf(links.getHref()));
                }
            }

            Payment save = paymentRepository.save(payment);
            log.info("Payment created: {}", save);
        } catch (PayPalRESTException e) {
            payment.setStatus(PaymentStatus.FAILED);
            paymentRepository.save(payment);
        }
    }
}
