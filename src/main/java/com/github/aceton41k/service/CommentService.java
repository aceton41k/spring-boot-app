package com.github.aceton41k.service;

import com.github.aceton41k.dto.CommentDto;
import com.github.aceton41k.entity.CommentEntity;
import com.github.aceton41k.entity.PostEntity;
import com.github.aceton41k.repository.CommentRepository;
import com.github.aceton41k.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    public Optional<?> getCommentsByPostId(Long postId, Integer page, Integer size) {
        if (postRepository.existsById(postId)) {
            Pageable pageable = PageRequest.of(page, size);
            return Optional.of(getAllComments(pageable));
        } else
            return Optional.empty();
    }

    public Optional<?> addComment(Long postId, CommentEntity comment) {
        Optional<PostEntity> postEntityOptional = postRepository.findById(postId);
        if (postEntityOptional.isPresent()) {
            comment.setPost(postEntityOptional.get());
            CommentEntity savedComment = commentRepository.save(comment);
            return Optional.of(convertToDto(savedComment));
        } else
            return Optional.empty();
    }


    public Optional<CommentDto> updateComment(Long postId, Long commentId, CommentEntity comment) {
        if (!postRepository.existsById(postId))
            throw new EntityNotFoundException("Post with id %d was not found".formatted(postId));

        // Поиск комментария по ID
        return commentRepository.findById(commentId)
                .map(existingComment -> {
                    // Обновляем данные комментария
                    existingComment.setMessage(comment.getMessage());
                    CommentEntity savedComment = commentRepository.save(existingComment);

                    // Возвращаем обновленный комментарий в виде DTO
                    return convertToDto(savedComment);
                });
    }


    public Optional<?> deleteComment(Long postId, Long commentId) {
        if (!postRepository.existsById(postId))
            return Optional.of("Post with id %d was not found".formatted(postId));

        if (commentRepository.existsById(commentId)) {
            commentRepository.deleteById(commentId);
            return Optional.empty();
        } else
            return Optional.of("Comment with id %d was not found".formatted(commentId));

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