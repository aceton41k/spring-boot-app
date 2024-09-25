package com.github.aceton41k.controller;

import com.github.aceton41k.dto.CommentDto;
import com.github.aceton41k.entity.CommentEntity;
import com.github.aceton41k.service.CommentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Optional;

import static com.github.aceton41k.controller.PostController.postNotFoundResponse;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping
    public ResponseEntity<?> getCommentsByPostId(@PathVariable Long postId, @RequestParam(value = "page", defaultValue = "0") int page,
                                                 @RequestParam(value = "size", defaultValue = "10") int size) {
        Optional<?> opt = commentService.getCommentsByPostId(postId, page, size);

        return opt.
                <ResponseEntity<?>>map(o -> ResponseEntity.ok(opt.get()))
                .orElseGet(() -> postNotFoundResponse(postId));
//
//        if (opt.isPresent())
//            return ResponseEntity.ok(opt.get());
//        else
//            return postNotFoundResponse(postId);

    }

    @PostMapping("/{commentId}")
    public ResponseEntity<?> addComment(@PathVariable Long postId, @RequestBody CommentEntity comment) {
        Optional<?> opt = commentService.addComment(postId, comment);

        if (opt.isPresent()) {
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(((CommentDto) opt.get()).getId())
                    .toUri();
            return ResponseEntity.created(location).body(opt.get());
        } else
            return entityNotFoundResponse("Post with id %d was not found".formatted(postId));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable Long postId, @PathVariable Long commentId, @RequestBody CommentEntity comment) {
        try {
            Optional<?> updatedCommentDto = commentService.updateComment(postId, commentId, comment);
            return updatedCommentDto
                    .<ResponseEntity<?>>map(ResponseEntity::ok) // Возвращаем статус 200 и обновленный комментарий
                    .orElseGet(() -> entityNotFoundResponse("Comment with id %d was not found".formatted(commentId)));

        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long postId, @PathVariable Long commentId) {
        Optional<?> opt = commentService.deleteComment(postId, commentId);
        if (opt.isPresent())
            return entityNotFoundResponse((String) opt.get());
        else
            return ResponseEntity.noContent().build();
    }

    static ResponseEntity<?> entityNotFoundResponse(String message) {
        var error = new HashMap<>();
        error.put("error", message);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
