package com.github.aceton41k.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class CommentDto {
    private String id;
    private String message;
    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
}
