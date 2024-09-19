package com.github.aceton41k.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Date;

@Getter
@Setter
public class PostDto {
    private Long id;
    private String title;
    private String message;
    private Instant createdAt;
    private Instant updatedAt;
    private Long createdBy;
    private Long modifiedBy;
}
