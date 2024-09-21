package com.github.aceton41k.controller;

import com.github.aceton41k.constant.SwaggerExamples;
import com.github.aceton41k.dto.PostDto;
import com.github.aceton41k.entity.PostEntity;
import com.github.aceton41k.repository.PostRepository;
import com.github.aceton41k.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    @Operation(summary = "Create post", description = "Returns created post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully registered",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostDto.class))),
    })
    @PostMapping("/posts")
    public ResponseEntity<PostDto> createPost(@RequestBody PostEntity post) {
        return postService.createPost(post);
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable("id") long id, @RequestBody PostEntity updatedPost) {
        return postService.updatePost(id, updatedPost);
    }

    @Operation(summary = "Get all posts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns list of post with pagination",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostDto.class),
                    examples = @ExampleObject(value = SwaggerExamples.GET_POSTS_RESPONSE)))
    })
    @GetMapping("/posts")
    public ResponseEntity<Page<PostDto>> getAllPosts(@RequestParam(value = "page", defaultValue = "0") int page,
                                                     @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PostDto> posts = postService.getAllPosts(pageable);
        return ResponseEntity.ok(posts);
    }

    @Operation(summary = "Get post by ID")
    @GetMapping("/posts/{id}")
    public ResponseEntity<?> getPostById(@PathVariable("id") Long id) {
        return postService.getPostById(id);
    }

    @Operation(summary = "Delete post by ID")
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable("id") Long id) {
        return postService.deletePost(id);
    }

    @Operation(summary = "Delete posts by list of IDs")
    @DeleteMapping("/posts")
    public ResponseEntity<Void> deletePosts(@RequestParam List<Long> ids) {
        return postService.deletePosts(ids);
    }

    @Operation(summary = "Method with exception for example")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "500",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class),
                        examples = @ExampleObject(value = SwaggerExamples.INTERNAL_ERROR_RESPONSE)))
    })
    @GetMapping("/ex")
    public ResponseEntity<?> ex() {
        throw new RuntimeException("Some error");
    }
}
