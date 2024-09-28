package com.ecom.security_service.dto;

public record ChangePassword(long userId, String oldPassword, String newPassword) {
}
