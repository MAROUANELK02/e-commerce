package com.ecom.user_service.services;

import com.ecom.user_service.dtos.UserRequestDTO;
import com.ecom.user_service.entities.User;
import com.ecom.user_service.exceptions.UserNotFoundException;

public interface UserService {
    User getUserById(Long id) throws UserNotFoundException;
    User getUserByEmail(String email) throws UserNotFoundException;
    User createUser(UserRequestDTO user);
    User createVendor(User user);
    User updateUser(User user);
    void deleteUser(Long id);
}
