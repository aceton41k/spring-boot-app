package app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @PostMapping("/posts")
    public Post createPost(@RequestBody Post post) {
        return postRepository.save(post);
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable("id") int id, @RequestBody Post updatedPost) {
        Optional<Post> existingPostOptional = postRepository.findById(id);
        if (existingPostOptional.isPresent()) {
            Post existingPost = existingPostOptional.get();

            existingPost.setTitle(updatedPost.getTitle());
            existingPost.setMessage(updatedPost.getMessage());
            existingPost.setDate(updatedPost.getDate());

            Post savedPost = postRepository.save(existingPost);
            return ResponseEntity.ok(savedPost);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/posts")
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @GetMapping("/posts/{id}")
    public Post getPostById(@PathVariable("id") int id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("app.Post not found with id: " + id));
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable("id") int id) {
        if (postRepository.existsById(id)) {
            postRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
