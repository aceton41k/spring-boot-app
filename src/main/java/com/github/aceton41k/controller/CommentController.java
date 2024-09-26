package com.github.aceton41k.controller;

import com.github.aceton41k.dto.CommentDto;
import com.github.aceton41k.entity.CommentEntity;
import com.github.aceton41k.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping
    public ResponseEntity<Page<CommentDto>> getCommentsByPostId(@PathVariable Long postId, @RequestParam(value = "page", defaultValue = "0") int page,
                                                                @RequestParam(value = "size", defaultValue = "10") int size) {
        Page<CommentDto> comments = commentService.getCommentsByPostId(postId, page, size);
        return ResponseEntity.ok().body(comments);

    }


    @PostMapping
    public ResponseEntity<?> addComment(@PathVariable Long postId, @RequestBody CommentEntity comment) {
        CommentDto commentDto = commentService.addComment(postId, comment);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(commentDto.getId())
                .toUri();
        return ResponseEntity.created(location).body(commentDto);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<?> getComment(@PathVariable Long postId, @PathVariable Long commentId) {
        CommentDto comment = commentService.getComment(postId, commentId);
        return ResponseEntity.ok().body(comment);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable Long postId, @PathVariable Long commentId, @RequestBody CommentEntity comment) {
        CommentDto updatedCommentDto = commentService.updateComment(postId, commentId, comment);
        return ResponseEntity.ok(updatedCommentDto);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long postId, @PathVariable Long commentId) {
        commentService.deleteComment(postId, commentId);
        return ResponseEntity.noContent().build();

    }
}
