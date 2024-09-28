package com.ecom.security_service.exceptions;

public class PasswordsNotMatchException extends Exception {
    public PasswordsNotMatchException(String oldPasswordNotMatch) {
        super(oldPasswordNotMatch);
    }
}
