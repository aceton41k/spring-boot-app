package com.github.aceton41k.repository;

import com.github.aceton41k.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface PostRepository extends MongoRepository<Post, Integer> {

    @NonNull Page<Post> findAll(@NonNull Pageable pageable);

    @NonNull
    Optional<Post> findById(@NonNull String id);

    boolean existsById(String id);
    void deleteById(String id);
}
