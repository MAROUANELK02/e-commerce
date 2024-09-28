package com.ecom.user_service.dtos;

import lombok.*;

@Data
public class UserRequestDTO {
    private String first_name;
    private String last_name;
    private String phone;
    private String address;
    private String country;
    private String city;
    private SignUpDTO signUpDTO;
}
