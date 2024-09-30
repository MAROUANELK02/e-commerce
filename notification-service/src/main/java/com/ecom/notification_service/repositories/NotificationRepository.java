package com.ecom.notification_service.repositories;

import com.ecom.notification_service.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
