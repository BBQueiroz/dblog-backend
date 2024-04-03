package com.dovisen.dblog.domain.Journey;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@Table
public interface JourneyNodeRepository extends JpaRepository<JourneyNode, UUID> {
}
