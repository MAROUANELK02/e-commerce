package com.ecom.payment_service.kafka;

import com.ecom.payment_service.dtos.PaymentCreated;
import com.ecom.payment_service.entities.Payment;
import com.ecom.payment_service.mappers.PaymentMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {
    private final PaymentMapper paymentMapper;
    private final KafkaTemplate<String, PaymentCreated> kafkaTemplate;

    @Value("${payment-processed}")
    private String topic;

    public KafkaProducer(PaymentMapper paymentMapper, KafkaTemplate<String, PaymentCreated> kafkaTemplate) {
        this.paymentMapper = paymentMapper;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(Payment payment) {

        Message<PaymentCreated> message = MessageBuilder
                .withPayload(paymentMapper.toPaymentCreated(payment))
                .setHeader(KafkaHeaders.TOPIC, topic).build();

        kafkaTemplate.send(message);
    }
}
