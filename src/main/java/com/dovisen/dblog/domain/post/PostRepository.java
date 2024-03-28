package com.dovisen.dblog.domain.post;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {

    boolean existsByTitle(String title);
}
