package com.dovisen.dblog.domain.post;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;
import java.util.List;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {

    List<Post> findByParentId(UUID id);
    List<Post> findTopNByData(Pageable pageable);
    boolean existsByTitle(String title);
}
