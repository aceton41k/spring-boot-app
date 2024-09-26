package com.github.aceton41k.controller;

import com.github.aceton41k.constant.ErrorMessages;
import com.github.aceton41k.dto.LoginResponse;
import com.github.aceton41k.dto.LoginUserDto;
import com.github.aceton41k.dto.RegisterRequest;
import com.github.aceton41k.dto.RegisterResponse;
import com.github.aceton41k.entity.UserEntity;
import com.github.aceton41k.service.AuthenticationService;
import com.github.aceton41k.service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityExistsException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RequestMapping("/auth")
@RestController
public class AuthController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @Operation(summary = "Registration", description = "Returns registered user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully registered",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RegisterResponse.class))),
            @ApiResponse(responseCode = "409",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EntityExistsException.class)))
    })
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody RegisterRequest registerUserDto) {
        if (authenticationService.userExistsByEmail(registerUserDto.getEmail())) {
            throw new EntityExistsException(ErrorMessages.EMAIL_ALREADY_EXISTS);
        }

        RegisterResponse registeredUser = authenticationService.signup(registerUserDto);

        return ResponseEntity.ok(registeredUser);
    }

    @Operation(summary = "Login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully logged in",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "401", description = "Invalid email or password",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"error\": \"" + ErrorMessages.INVALID_EMAIL_PASSWORD + "\"}"),
                            schema = @Schema(implementation = ResponseStatusException.class)))
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginUserDto loginUserDto) {
        UserEntity authenticatedUser = authenticationService.authenticate(loginUserDto);

        if (authenticatedUser == null) {
            throw new BadCredentialsException(ErrorMessages.INVALID_EMAIL_PASSWORD);

        } else {
            String jwtToken = jwtService.generateToken(authenticatedUser);
            LoginResponse loginResponse = new LoginResponse().withToken(jwtToken).withExpiresIn(jwtService.getExpirationTime());
            return ResponseEntity.ok(loginResponse);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String token = jwtService.extractTokenFromRequest(request);
        jwtService.invalidateToken(token);
        return ResponseEntity.ok("You have successfully signed out");
    }
}