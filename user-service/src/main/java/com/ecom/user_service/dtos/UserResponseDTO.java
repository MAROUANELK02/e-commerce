package com.ecom.user_service.dtos;

import lombok.*;

@Data
public class UserResponseDTO {
    private Long userId;
    private String first_name;
    private String last_name;
    private String username;
    private String password;
    private String email;
    private RoleDTO roleDTO;
}
