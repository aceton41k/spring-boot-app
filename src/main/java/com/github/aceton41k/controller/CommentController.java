package com.github.aceton41k.controller;

import com.github.aceton41k.dto.CommentsResponse;
import com.github.aceton41k.entity.CommentEntity;
import com.github.aceton41k.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping
    public ResponseEntity<CommentsResponse> getCommentsByPostId(@PathVariable Long postId) {
        return commentService.getCommentsByPostId(postId);
    }

    @PostMapping
    public ResponseEntity<?> addComment(@PathVariable Long postId, @RequestBody CommentEntity comment) {
      return commentService.addComment(postId, comment);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable long postId, @PathVariable long commentId, @RequestBody CommentEntity comment) {
        return commentService.updateComment(postId, commentId, comment);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable long postId, @PathVariable long commentId) {
        return commentService.deleteComment(postId, commentId);
    }
}
