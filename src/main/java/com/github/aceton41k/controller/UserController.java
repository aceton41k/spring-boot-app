package com.github.aceton41k.controller;

import com.github.aceton41k.dto.UserDto;
import com.github.aceton41k.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api")
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> me() {
       return userService.me();
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> allUsers() {
        List<UserDto> users = userService.allUsers();

        return ResponseEntity.ok(users);
    }
}