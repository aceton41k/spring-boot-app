package com.github.aceton41k.dto;

import lombok.*;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
public class CommentsResponse {
    private List<CommentDto> comments;
    private Integer total;
}
