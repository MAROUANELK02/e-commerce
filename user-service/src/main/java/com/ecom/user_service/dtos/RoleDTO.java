package com.ecom.user_service.dtos;

import com.ecom.user_service.enums.ERole;
import lombok.*;

@Data
public class RoleDTO {
    private Long roleId;
    private ERole roleName;
}
