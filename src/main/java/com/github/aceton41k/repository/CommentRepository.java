package com.github.aceton41k.repository;

import com.github.aceton41k.entity.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, Integer> {
    List<Comment> findByPostId(String postId);
}