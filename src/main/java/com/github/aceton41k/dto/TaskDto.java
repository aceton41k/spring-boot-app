package com.github.aceton41k.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class TaskDto {

    private Long id;
    private Integer progress;
    private String status;
    private Instant createdAt;
    private Instant updatedAt;
    private Long createdBy;
}