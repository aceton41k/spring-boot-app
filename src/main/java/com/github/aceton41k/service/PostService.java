package com.github.aceton41k.service;

import com.github.aceton41k.dto.PostDto;
import com.github.aceton41k.entity.Post;
import com.github.aceton41k.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public PostDto createPost(Post post) {
        return convertToDto(postRepository.save(post));
    }

    public ResponseEntity<PostDto> updatePost(int id, Post updatedPost) {
        Optional<Post> existingPostOptional = postRepository.findById(id);
        if (existingPostOptional.isPresent()) {
            Post existingPost = existingPostOptional.get();
            existingPost.setTitle(updatedPost.getTitle());
            existingPost.setMessage(updatedPost.getMessage());
            Post savedPost = postRepository.save(existingPost);
            return ResponseEntity.ok((convertToDto(savedPost)));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public List<PostDto> getAllPosts() {
        return postRepository.findAll().stream().map(this::convertToDto).toList();
    }

    public ResponseEntity<?> getPostById(int id) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isPresent())
            return ResponseEntity.ok().body(convertToDto(post.get()));
        else return postNotFoundResponse(id);
    }

    public ResponseEntity<Void> deletePost(int id) {
        if (postRepository.existsById(id)) {
            postRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Void> deletePosts(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        for (Integer id : ids) {
            if (postRepository.existsById(id)) {
                postRepository.deleteById(id);
            }
        }
        return ResponseEntity.noContent().build();
    }

    public boolean existsById(int id) {
        return postRepository.existsById(id);
    }

    private PostDto convertToDto(Post post) {
        PostDto dto = new PostDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setMessage(post.getMessage());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setUpdatedAt(post.getUpdatedAt());
        return dto;
    }

    protected ResponseEntity<?> postNotFoundResponse(Integer postId) {
        var error = new HashMap<>();
        error.put("error", "Post with id %d was not found".formatted(postId));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}