package com.ecom.security_service.dto;

public record LoginDTO(String grantType,
                       String username,
                       String password,
                       String refreshToken) {
}
