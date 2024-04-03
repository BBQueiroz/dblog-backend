package com.dovisen.dblog.domain.post;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.UUID;
@Repository
@Table
public interface PostRepository extends JpaRepository<Post, UUID> {

    List<Post> findByParentId(UUID id);
    List<Post> findTopNByData(Pageable pageable);
    boolean existsByTitle(String title);
}
