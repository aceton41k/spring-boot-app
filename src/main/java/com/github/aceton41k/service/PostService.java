package com.github.aceton41k.service;

import com.github.aceton41k.dto.PostDto;
import com.github.aceton41k.entity.PostEntity;
import com.github.aceton41k.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.github.aceton41k.constant.ErrorMessages.POST_NOT_FOUND;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public PostDto createPost(PostEntity post) {
        PostEntity savedPost = postRepository.save(post);
        return convertToDto(savedPost);
    }

    public PostDto updatePost(Long id, PostEntity updatedPost) {

        return postRepository.findById(id)
                .map(postEntity -> {
                    postEntity.setTitle(updatedPost.getTitle());
                    postEntity.setMessage(updatedPost.getMessage());
                    PostEntity savedPost = postRepository.save(postEntity);
                    return convertToDto(savedPost);
                })
                .orElseThrow(() -> new EntityNotFoundException(POST_NOT_FOUND.formatted(id)));
    }

    public Page<PostDto> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable).map(this::convertToDto);
    }

    public PostDto getPostById(Long id) {
        Optional<PostEntity> post = postRepository.findById(id);

        return post
                .map(this::convertToDto)
                .orElseThrow(() -> new EntityNotFoundException(POST_NOT_FOUND.formatted(id)));

    }

    public void deletePost(Long id) {
        if (postRepository.existsById(id)) {
            postRepository.deleteById(id);
        } else
            throw new EntityNotFoundException(POST_NOT_FOUND.formatted(id));
    }

    public ResponseEntity<Void> deletePosts(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        ids.forEach(id -> {
            if (postRepository.existsById(id)) {
                postRepository.deleteById(id);
            }
        });

        return ResponseEntity.noContent().build();
    }

    private PostDto convertToDto(PostEntity post) {
        PostDto dto = new PostDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setMessage(post.getMessage());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setUpdatedAt(post.getUpdatedAt());
        dto.setCreatedBy(post.getCreatedBy());
        dto.setModifiedBy(post.getModifiedBy());
        return dto;
    }
}