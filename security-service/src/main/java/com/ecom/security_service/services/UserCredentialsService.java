package com.ecom.security_service.services;

import com.ecom.security_service.dto.LoginDTO;
import com.ecom.security_service.dto.LoginResponseDTO;
import com.ecom.security_service.dto.SignUpDTO;
import com.ecom.security_service.entities.Role;
import com.ecom.security_service.entities.UserCredentials;
import com.ecom.security_service.exceptions.PasswordsNotMatchException;
import com.ecom.security_service.exceptions.RoleNotFoundException;
import com.ecom.security_service.exceptions.UserCredentialsNotFoundException;

public interface UserCredentialsService {
    UserCredentials createUserCredentials(SignUpDTO signUpDTO, Role role);
    UserCredentials createUserCredentialsWithUserRole(SignUpDTO signUp) throws RoleNotFoundException;
    UserCredentials createUserCredentialsWithVendorRole(SignUpDTO signUp) throws RoleNotFoundException;
    UserCredentials createUserCredentialsWithAdminRole(SignUpDTO signUp) throws RoleNotFoundException;
    UserCredentials getUserCredentials(long userId) throws UserCredentialsNotFoundException;
    UserCredentials loadUserByUsername(String username) throws UserCredentialsNotFoundException;
    UserCredentials changePassword(long userId, String oldPassword, String newPassword) throws UserCredentialsNotFoundException, PasswordsNotMatchException;
    LoginResponseDTO login(LoginDTO loginDTO);
    void deleteUserCredentials(long userId) throws UserCredentialsNotFoundException;
}
