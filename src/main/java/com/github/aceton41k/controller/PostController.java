package com.github.aceton41k.controller;

import com.github.aceton41k.dto.PostDto;
import com.github.aceton41k.entity.Post;
import com.github.aceton41k.repository.PostRepository;
import com.github.aceton41k.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    @PostMapping("/posts")
    public PostDto createPost(@RequestBody Post post) {
        return postService.createPost(post);
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable("id") int id, @RequestBody Post updatedPost) {
       return postService.updatePost(id, updatedPost);
    }

    @GetMapping("/posts")
    public List<PostDto> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<?> getPostById(@PathVariable("id") int id) {
      return postService.getPostById(id);
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable("id") int id) {
       return postService.deletePost(id);
    }

    @DeleteMapping("/posts")
    public ResponseEntity<Void> deletePosts(@RequestParam List<Integer> ids) {
      return postService.deletePosts(ids);
    }

    @GetMapping("/ex")
    public ResponseEntity<?> ex() {
        throw new RuntimeException("Some error");
    }
}
