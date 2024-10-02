package com.ecom.notification_service.dtos;

public record EmailMessage(String destination, String subject, String message) {
}
