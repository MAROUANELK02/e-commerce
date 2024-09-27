package com.ecom.security_service.exceptions;

public class PasswordsNotMatchException extends Throwable {
    public PasswordsNotMatchException(String oldPasswordNotMatch) {
        super(oldPasswordNotMatch);
    }
}
