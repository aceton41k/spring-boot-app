package com.github.aceton41k.controller;

import com.github.aceton41k.dto.PostDto;
import com.github.aceton41k.entity.Post;
import com.github.aceton41k.repository.PostRepository;
import com.github.aceton41k.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    @PostMapping("/posts")
    public ResponseEntity<PostDto> createPost(@RequestBody Post post) {
        return postService.createPost(post);
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable("id") long id, @RequestBody Post updatedPost) {
        return postService.updatePost(id, updatedPost);
    }

    @GetMapping("/posts")
    public ResponseEntity<Page<PostDto>> getAllPosts(@RequestParam(value = "page", defaultValue = "0") int page,
                                                     @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PostDto> posts = postService.getAllPosts(pageable);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<?> getPostById(@PathVariable("id") Long id) {
        return postService.getPostById(id);
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable("id") Long id) {
        return postService.deletePost(id);
    }

    @DeleteMapping("/posts")
    public ResponseEntity<Void> deletePosts(@RequestParam List<Long> ids) {
        return postService.deletePosts(ids);
    }

    @GetMapping("/ex")
    public ResponseEntity<?> ex() {
        throw new RuntimeException("Some error");
    }
}
