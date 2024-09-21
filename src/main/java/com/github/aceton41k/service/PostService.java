package com.github.aceton41k.service;

import com.github.aceton41k.dto.PostDto;
import com.github.aceton41k.entity.PostEntity;
import com.github.aceton41k.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public ResponseEntity<PostDto> createPost(PostEntity post) {

        PostEntity savedPost = postRepository.save(post);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPost.getId())
                .toUri();

        return ResponseEntity.created(location)
                .body(convertToDto(savedPost));
    }

    public ResponseEntity<PostDto> updatePost(Long id, PostEntity updatedPost) {
        Optional<PostEntity> existingPostOptional = postRepository.findById(id);
        if (existingPostOptional.isPresent()) {
            PostEntity existingPost = existingPostOptional.get();
            existingPost.setTitle(updatedPost.getTitle());
            existingPost.setMessage(updatedPost.getMessage());
            PostEntity savedPost = postRepository.save(existingPost);
            return ResponseEntity.ok((convertToDto(savedPost)));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public Page<PostDto> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable).map(this::convertToDto);
    }

    public ResponseEntity<?> getPostById(Long id) {
        Optional<PostEntity> post = postRepository.findById(id);
        if (post.isPresent())
            return ResponseEntity.ok().body(convertToDto(post.get()));
        else return postNotFoundResponse(id);
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

    protected ResponseEntity<?> postNotFoundResponse(Long postId) {
        var error = new HashMap<>();
        error.put("error", "Post with id %s was not found".formatted(postId));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}