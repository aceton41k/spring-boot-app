package com.github.aceton41k.service;

import com.github.aceton41k.dto.CommentDto;
import com.github.aceton41k.entity.CommentEntity;
import com.github.aceton41k.repository.CommentRepository;
import com.github.aceton41k.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.github.aceton41k.constant.ErrorMessages.COMMENT_NOT_FOUND;
import static com.github.aceton41k.constant.ErrorMessages.POST_NOT_FOUND;

@Service
@Slf4j
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    public Page<CommentDto> getCommentsByPostId(Long postId, Integer page, Integer size) {
        if (postRepository.existsById(postId)) {
            Pageable pageable = PageRequest.of(page, size);
            return getAllComments(pageable);
        } else
            throw new EntityNotFoundException(POST_NOT_FOUND.formatted(postId));
    }

    public CommentDto getComment(Long postId, Long commentId) {

        if (!postRepository.existsById(postId))
            throw new EntityNotFoundException(POST_NOT_FOUND.formatted(postId));

        return commentRepository.findById(commentId)
                .map(this::convertToDto)
                .orElseThrow(() -> new EntityNotFoundException(COMMENT_NOT_FOUND.formatted(commentId)));
    }

    public CommentDto addComment(Long postId, CommentEntity comment) {

        return postRepository.findById(postId)
                .map(o -> {
                    comment.setPost(o);
                    return convertToDto(commentRepository.save(comment));
                })
                .orElseThrow(() -> new EntityNotFoundException(POST_NOT_FOUND.formatted(postId)));
    }


    public CommentDto updateComment(Long postId, Long commentId, CommentEntity comment) {
        if (!postRepository.existsById(postId))
            throw new EntityNotFoundException(POST_NOT_FOUND.formatted(postId));

        return commentRepository.findById(commentId)

                .map(existingComment -> {
                    existingComment.setMessage(comment.getMessage());
                    CommentEntity savedComment = commentRepository.save(existingComment);
                    return convertToDto(savedComment);
                })
                .orElseThrow(() -> new EntityNotFoundException(COMMENT_NOT_FOUND.formatted(commentId)));
    }


    public void deleteComment(Long postId, Long commentId) {
        if (!postRepository.existsById(postId))
            throw new EntityNotFoundException(POST_NOT_FOUND.formatted(postId));

        if (commentRepository.existsById(commentId)) {
            commentRepository.deleteById(commentId);
        } else
            throw new EntityNotFoundException(COMMENT_NOT_FOUND.formatted(commentId));

    }

    public Page<CommentDto> getAllComments(Pageable pageable) {
        return commentRepository.findAll(pageable).map(this::convertToDto);
    }

    private CommentDto convertToDto(CommentEntity comment) {
        CommentDto dto = new CommentDto();
        dto.setId(comment.getId());
        dto.setMessage(comment.getMessage());
        dto.setCreatedAt(comment.getCreatedAt());
        dto.setUpdatedAt(comment.getUpdatedAt());
        dto.setCreatedBy(comment.getCreatedBy());
        dto.setModifiedBy(comment.getModifiedBy());
        return dto;
    }
}