package com.github.aceton41k.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @Schema(example = "cthulhu@rlyeh.bz")
    private String email;
    @Schema(example = "123456")
    private String password;
    @Schema(example = "Cthulhu")
    private String fullName;
}