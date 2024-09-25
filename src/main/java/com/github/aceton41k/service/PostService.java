package com.github.aceton41k.service;

import com.github.aceton41k.dto.PostDto;
import com.github.aceton41k.entity.PostEntity;
import com.github.aceton41k.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public PostDto createPost(PostEntity post) {
        PostEntity savedPost = postRepository.save(post);
        return convertToDto(savedPost);
    }

    public Optional<PostDto> updatePost(Long id, PostEntity updatedPost) {
        Optional<PostEntity> existingPostOptional = postRepository.findById(id);
        if (existingPostOptional.isPresent()) {
            PostEntity existingPost = existingPostOptional.get();
            existingPost.setTitle(updatedPost.getTitle());
            existingPost.setMessage(updatedPost.getMessage());
            PostEntity savedPost = postRepository.save(existingPost);
            return Optional.of(convertToDto(savedPost));
        } else {
            return Optional.empty();
        }
    }

    public Page<PostDto> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable).map(this::convertToDto);
    }

    public PostDto getPostById(Long id) {
        Optional<PostEntity> post = postRepository.findById(id);
        return post.map(this::convertToDto).orElse(null);
    }

    public ResponseEntity<Void> deletePost(Long id) {
        if (postRepository.existsById(id)) {
            postRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Void> deletePosts(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        for (Long id : ids) {
            if (postRepository.existsById(id)) {
                postRepository.deleteById(id);
            }
        }
        return ResponseEntity.noContent().build();
    }

    public boolean existsById(Long id) {
        return postRepository.existsById(id);
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