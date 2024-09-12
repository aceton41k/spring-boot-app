package com.github.aceton41k.repository;

import com.github.aceton41k.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PostRepository extends JpaRepository<Post, Integer> {
}
