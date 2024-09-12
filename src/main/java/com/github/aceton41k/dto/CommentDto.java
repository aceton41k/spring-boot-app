package com.github.aceton41k.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CommentDto {
    private Integer id;
    private String message;
    private Date createdAt;
    private Date updatedAt;
}
