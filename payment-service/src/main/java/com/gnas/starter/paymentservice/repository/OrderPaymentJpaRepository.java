package com.gnas.starter.paymentservice.repository;

import com.gnas.starter.paymentservice.domain.OrderPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderPaymentJpaRepository extends JpaRepository<OrderPayment, UUID> {}
