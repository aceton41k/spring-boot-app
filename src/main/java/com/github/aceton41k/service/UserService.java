package com.github.aceton41k.service;


import com.github.aceton41k.dto.CommentDto;
import com.github.aceton41k.dto.UserDto;
import com.github.aceton41k.entity.Comment;
import com.github.aceton41k.entity.User;
import com.github.aceton41k.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDto> allUsers() {
        List<User> users = new ArrayList<>();

        userRepository.findAll().forEach(users::add);

        return users.stream().map(this::convertToDto).toList();
    }

    public ResponseEntity<UserDto> me() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        return ResponseEntity.ok(convertToDto(currentUser));
    }

    private UserDto convertToDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFullName(user.getFullName());
        dto.setUpdatedAt(user.getUpdatedAt());
        dto.setCreatedAt(user.getCreatedAt());
        return dto;
    }
}