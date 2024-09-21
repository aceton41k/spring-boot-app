package com.github.aceton41k.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterResponse {
    private Long id;
    @Schema(example = "cthulhu@rlyeh.bz")
    private String email;
    @Schema(example = "123456")
    private String password;
    @Schema(example = "Cthulhu")
    private String fullName;
}
