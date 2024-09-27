package com.ecom.security_service.exceptions;

public class RoleNotFoundException extends Exception {
    public RoleNotFoundException(String roleNotFound) {
        super(roleNotFound);
    }
}
