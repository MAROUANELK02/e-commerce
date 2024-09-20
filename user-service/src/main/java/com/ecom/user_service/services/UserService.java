package com.ecom.user_service.services;

import com.ecom.user_service.entities.User;
import com.ecom.user_service.exceptions.RoleNotFoundException;
import com.ecom.user_service.exceptions.UserNotFoundException;

public interface UserService {
    User getUserById(Long id) throws UserNotFoundException;
    User getUserByEmail(String email) throws UserNotFoundException;
    User getUserByUsername(String username) throws UserNotFoundException;
    User createUser(User user) throws RoleNotFoundException;
    User updateUser(User user);
    void deleteUser(Long id);
}
