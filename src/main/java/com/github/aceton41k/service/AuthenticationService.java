package com.github.aceton41k.service;

import com.github.aceton41k.dto.LoginUserDto;
import com.github.aceton41k.dto.RegisterUserDto;
import com.github.aceton41k.entity.User;
import com.github.aceton41k.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User signup(RegisterUserDto input) {
        User user = new User();
                user.setFullName(input.getFullName());
                user.setEmail(input.getEmail());
                user.setPassword(passwordEncoder.encode(input.getPassword()));

        return userRepository.save(user);
    }

    public boolean userExistsByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public User authenticate(LoginUserDto input) {
        Authentication authentication;

        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            input.getEmail(),
                            input.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            return null;
        }

        return (User) authentication.getPrincipal();
    }
}