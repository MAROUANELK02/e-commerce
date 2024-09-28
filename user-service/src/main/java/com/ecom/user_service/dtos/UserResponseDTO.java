package com.ecom.user_service.dtos;

import lombok.*;

@Data
public class UserResponseDTO {
    private Long userId;
    private String first_name;
    private String last_name;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String country;
}
