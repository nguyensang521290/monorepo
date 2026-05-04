package com.gnas.starter.orderservice.repository;

import com.gnas.starter.orderservice.domain.OrderData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderJpaRepository extends JpaRepository<OrderData, UUID> {}
