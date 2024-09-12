package com.github.aceton41k.controller;

import com.github.aceton41k.dto.CommentsResponse;
import com.github.aceton41k.entity.Comment;
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
    public ResponseEntity<CommentsResponse> getCommentsByPostId(@PathVariable Integer postId) {
        return commentService.getCommentsByPostId(postId);
    }

    @PostMapping
    public ResponseEntity<?> addComment(@PathVariable Integer postId, @RequestBody Comment comment) {
      return commentService.addComment(postId, comment);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable Integer postId, @PathVariable Integer commentId, @RequestBody Comment comment) {
        return commentService.updateComment(postId, commentId, comment);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Integer postId, @PathVariable Integer commentId) {
        return commentService.deleteComment(postId, commentId);
    }
}
