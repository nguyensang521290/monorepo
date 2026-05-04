package com.gnas.starter.orderservice.repository;

import com.gnas.starter.orderservice.domain.OutboxEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OutboxEventJpaRepository extends JpaRepository<OutboxEvent, UUID> {
    List<OutboxEvent> findTop50ByIsPublishedFalseOrderByCreatedAtAsc();
}
