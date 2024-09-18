package com.github.aceton41k.repository;

import com.github.aceton41k.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface PostRepository extends JpaRepository<Post, Integer> {

    @NonNull Page<Post> findAll(@NonNull Pageable pageable);
}
