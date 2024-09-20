package com.ecom.user_service.services;

import com.ecom.user_service.entities.Role;
import com.ecom.user_service.entities.User;
import com.ecom.user_service.enums.ERole;
import com.ecom.user_service.exceptions.RoleNotFoundException;
import com.ecom.user_service.exceptions.UserNotFoundException;
import com.ecom.user_service.repositories.RoleRepository;
import com.ecom.user_service.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public User getUserById(Long id) throws UserNotFoundException {
        log.info("get User By Id : {}", id);
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        }else{
            throw new UserNotFoundException("User not found");
        }
    }

    @Override
    public User getUserByEmail(String email) throws UserNotFoundException {
        log.info("get User By email : {}", email);
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            return user.get();
        }else{
            throw new UserNotFoundException("User not found");
        }
    }

    @Override
    public User getUserByUsername(String username) throws UserNotFoundException {
        log.info("get User By username : {}", username);
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            return user.get();
        }else{
            throw new UserNotFoundException("User not found");
        }
    }

    @Override
    public User createUser(User user) throws RoleNotFoundException {
        log.info("create User : {}", user);
        Optional<Role> role = roleRepository.findByRoleName(ERole.USER);
        if(role.isPresent()){
            user.setRole(role.get());
            return userRepository.save(user);
        }else{
            throw new RoleNotFoundException("Role not found");
        }
    }

    @Override
    public User updateUser(User user) {
        try {
            User userFound = getUserById(user.getUserId());
            if(!userFound.getFirst_name().equals(user.getFirst_name()))
                userFound.setFirst_name(user.getFirst_name());
            if(!userFound.getLast_name().equals(user.getLast_name()))
                userFound.setLast_name(user.getLast_name());
            if(!userFound.getEmail().equals(user.getEmail()))
                userFound.setEmail(user.getEmail());
            if(!userFound.getUsername().equals(user.getUsername()))
                userFound.setUsername(user.getUsername());
            if(!userFound.getPassword().equals(user.getPassword()))
                userFound.setPassword(user.getPassword());
            log.info("update User : {}", userFound);
            return userRepository.save(userFound);
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteUser(Long id) {
        try {
            User user = getUserById(id);
            userRepository.delete(user);
            log.info("Deleted User : {}", id);
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
