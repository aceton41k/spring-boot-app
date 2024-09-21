package com.github.aceton41k.service;

import com.github.aceton41k.dto.CommentDto;
import com.github.aceton41k.dto.CommentsResponse;
import com.github.aceton41k.entity.CommentEntity;
import com.github.aceton41k.entity.PostEntity;
import com.github.aceton41k.repository.CommentRepository;
import com.github.aceton41k.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    public ResponseEntity<CommentsResponse> getCommentsByPostId(Long postId) {
        List<CommentEntity> comments = commentRepository.findByPostId(postId);
        List<CommentDto> commentDtos = comments.stream()
                .map(this::convertToDto)
                .toList();
        return ResponseEntity.ok(new CommentsResponse()
                .withComments(commentDtos)
                .withTotal(comments.size()));
    }

    public ResponseEntity<?> addComment(Long postId, CommentEntity comment) {
        if (!postService.existsById(postId)) {
            return postService.postNotFoundResponse(postId);
        }

        comment.setPost(new PostEntity().withId(postId));
        CommentEntity savedComment = commentRepository.save(comment);
        return ResponseEntity.ok(savedComment);
    }

    public ResponseEntity<?> updateComment(Long postId, Long commentId, CommentEntity comment) {

        Optional<PostEntity> postOptional = postRepository.findById(postId);
        PostEntity post;

        if (postOptional.isEmpty()) {
            return postService.postNotFoundResponse(postId);
        } else
            post = postOptional.get();

        Optional<CommentEntity> commentOptional = commentRepository.findById(commentId);
        CommentEntity updatedComment;

        if (commentOptional.isEmpty()) {
            return commentNotFoundResponse(commentId);
        } else
            updatedComment = commentOptional.get();

        updatedComment.setMessage(comment.getMessage());
        updatedComment.setPost(post);

        return ResponseEntity.ok(convertToDto(commentRepository.save(updatedComment)));
    }

    public ResponseEntity<?> deleteComment(Long postId, Long commentId) {
        if (!postRepository.existsById(postId)) {
            return postService.postNotFoundResponse(postId);
        }

        if (!commentRepository.existsById(commentId)) {
            return commentNotFoundResponse(commentId);
        }
        commentRepository.deleteById(commentId);
        return ResponseEntity.noContent().build();
    }

    private CommentDto convertToDto(CommentEntity comment) {
        CommentDto dto = new CommentDto();
        dto.setId(comment.getId());
        dto.setMessage(comment.getMessage());
        dto.setCreatedAt(comment.getCreatedAt());
        dto.setUpdatedAt(comment.getUpdatedAt());
        dto.setCreatedBy(comment.getCreatedBy());
        return dto;
    }

    protected ResponseEntity<?> commentNotFoundResponse(Long commentId) {
        var error = new HashMap<>();
        error.put("error", "Comment with id %d was not found".formatted(commentId));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}