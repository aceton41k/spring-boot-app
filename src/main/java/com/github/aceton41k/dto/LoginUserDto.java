package com.github.aceton41k.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginUserDto {
    private String email;

    private String password;
}