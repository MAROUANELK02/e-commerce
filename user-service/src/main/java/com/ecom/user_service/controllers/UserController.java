package com.ecom.user_service.controllers;

import com.ecom.user_service.dtos.UserRequestDTO;
import com.ecom.user_service.mappers.UserMapper;
import com.ecom.user_service.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUser(@PathVariable(name = "id") long id) {
        try {
            return ResponseEntity.ok(userMapper.toUserResponseDTO(userService.getUserById(id)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/saveUser")
    public ResponseEntity<?> saveUser(@RequestBody UserRequestDTO user) {
        try {
            return ResponseEntity.ok(userMapper.toUserResponseDTO(userService.createUser(user)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(name = "id") long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok().body("User deleted successfully");
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
