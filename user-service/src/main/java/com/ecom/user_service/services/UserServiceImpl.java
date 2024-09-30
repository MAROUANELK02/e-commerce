package com.ecom.user_service.services;

import com.ecom.user_service.dtos.SignUpDTO;
import com.ecom.user_service.dtos.UserRequestDTO;
import com.ecom.user_service.entities.User;
import com.ecom.user_service.exceptions.UserNotFoundException;
import com.ecom.user_service.mappers.UserMapper;
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
    private final SecurityService securityService;
    private final UserMapper userMapper;

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
    public User createUser(UserRequestDTO user) {
        log.info("create User : {}", user);
        User save = userRepository.save(userMapper.toUser(user));
        SignUpDTO userCredentials = new SignUpDTO(user.getSignUpDTO().username(),
                user.getSignUpDTO().password(),user.getSignUpDTO().email(),save.getUserId());
        securityService.signUp(userCredentials);
        return save;
    }

    @Override
    public User createVendor(UserRequestDTO user) {
        log.info("create Vendor");
        User save = userRepository.save(userMapper.toUser(user));
        SignUpDTO userCredentials = new SignUpDTO(user.getSignUpDTO().username(),
                user.getSignUpDTO().password(),user.getSignUpDTO().email(),save.getUserId());
        securityService.signUpVendor(userCredentials);
        return save;
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
            if(!userFound.getPhone().equals(user.getPhone()))
                userFound.setPhone(user.getPhone());
            if(!userFound.getCity().equals(user.getCity()))
                userFound.setCity(user.getCity());
            if(!userFound.getCountry().equals(user.getCountry()))
                userFound.setCountry(user.getCountry());
            if(!userFound.getAddress().equals(user.getAddress()))
                userFound.setAddress(user.getAddress());
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
