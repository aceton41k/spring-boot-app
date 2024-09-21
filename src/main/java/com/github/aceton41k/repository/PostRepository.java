package com.github.aceton41k.repository;

import com.github.aceton41k.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
    @NonNull
    Page<PostEntity> findAll(@NonNull Pageable pageable);
}