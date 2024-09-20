package com.ecom.user_service.mappers;

import com.ecom.user_service.dtos.UserRequestDTO;
import com.ecom.user_service.dtos.UserResponseDTO;
import com.ecom.user_service.entities.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserMapper {
    private final RoleMapper roleMapper;
    public User toUser(UserRequestDTO userRequestDTO) {
        User user = new User();
        BeanUtils.copyProperties(userRequestDTO, user);
        return user;
    }

    public UserResponseDTO toUserResponseDTO(User user) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        BeanUtils.copyProperties(user, userResponseDTO);
        userResponseDTO.setRoleDTO(roleMapper.toRoleDTO(user.getRole()));
        return userResponseDTO;
    }
}
