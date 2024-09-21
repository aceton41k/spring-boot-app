package com.github.aceton41k.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    @Schema(description = "JWT token", example = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhQGEuYSIsImlhdCI6MTcyNjkxMDA2MiwiZXhwIjoxNzI2OTI4MDYyfQ.wGcIVT61NwqG-GhZgVtXy3eIK-WfJ-8RIMXgDY0fkN88thx2kvEz5jaUx3O2D23C")
    private String token;

    @Schema(description = "Expires time in ms", example = "18000000")
    private long expiresIn;

}
