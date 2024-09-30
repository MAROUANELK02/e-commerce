package com.ecom.user_service.dtos;

import lombok.Builder;

@Builder
public record SignUpDTO(String username, String password, String email, long userId) {
}
