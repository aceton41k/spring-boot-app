package com.github.aceton41k.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class Task {
//    private final AtomicInteger progress = new AtomicInteger(0);
//    private volatile String status = "not started";

    private Long id;
    private Integer progress;
    private String status;
    private Instant createdAt;
    private Instant updatedAt;
    private Long createdBy;
}