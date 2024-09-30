package com.ecom.notification_service.kafka;

import com.ecom.notification_service.dtos.PaymentCreated;
import com.ecom.notification_service.entities.Notification;
import com.ecom.notification_service.enums.NotificationStatus;
import com.ecom.notification_service.repositories.NotificationRepository;
import com.ecom.notification_service.services.EmailSenderService;
import com.ecom.notification_service.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@AllArgsConstructor
public class NotificationConsumer {
    private final EmailSenderService emailSenderService;
    private final UserService userService;
    private final NotificationRepository notificationRepository;

    @KafkaListener(topics = "${topic}", groupId = "${group-id}")
    public void consumeOrderCreated(PaymentCreated paymentCreated) {
        String subject = "Confirmation de paiement";
        String message = String.format("""
                Commande effectuée %d et paiment confirmé %d dans l'instant %s !
                merci de votre confiance
                """, paymentCreated.getOrderId(), paymentCreated.getPaymentId(),
                paymentCreated.getPaymentDate().toString());
        String email = userService.getUserMail(paymentCreated.getUserId());
        Notification notification = notificationRepository.save(Notification.builder()
                .userId(paymentCreated.getUserId())
                .orderId(paymentCreated.getOrderId())
                .userMailAddress(email)
                .message(message)
                .sentDate(LocalDateTime.now())
                .status(NotificationStatus.PENDING)
                .build());
        try {
            emailSenderService.sendEmail(email, subject, message);
            notification.setStatus(NotificationStatus.SENT);
        }catch (Exception e) {
            notification.setStatus(NotificationStatus.FAILED);
        }finally {
            notificationRepository.save(notification);
        }
    }
}
