package com.dovisen.dblog.domain.Journey;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JourneyNodeRepository extends JpaRepository<JourneyNode, UUID> {
}
