package com.github.aceton41k.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class PostDto {
    @Schema(example = "123")
    private Long id;

    @Schema(example = "All your base are belong to us")
    private String title;

    @Schema(example = "Message about your poor life")
    private String message;

    @Schema(example = "2024-09-18T17:12:56.1723430Z")
    private Instant createdAt;

    @Schema(example = "2024-09-19T22:27:13.572460Z")
    private Instant updatedAt;

    @Schema(example = "666")
    private Long createdBy;

    @Schema(example = "666")
    private Long modifiedBy;
}
