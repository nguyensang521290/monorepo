package com.gnas.starter.orderservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gnas.starter.orderservice.controller.OrderRequest;
import com.gnas.starter.orderservice.domain.OrderData;
import com.gnas.starter.orderservice.domain.OrderStatus;
import com.gnas.starter.orderservice.repository.OrderJpaRepository;
import com.gnas.starter.orderservice.service.outbox.OrderWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderJpaRepository orderJpaRepository;
    private final OrderWriter orderWriter;

    @Transactional
    public void order(OrderRequest request) throws JsonProcessingException {
        try {
            OrderData orderData = OrderData.builder()
                    .status(OrderStatus.CREATED)
                    .products(Optional.ofNullable(request.getProducts()).orElse(Collections.emptyList()))
                    .build();

            OrderData savedOrder = orderJpaRepository.saveAndFlush(orderData);

            orderWriter.write(savedOrder);

            log.info("OrderService::order Successfully created order with id={}", savedOrder.getOrderId());

        } catch (Exception exception) {
            log.error("OrderService::order Failed to create order with error={}", exception.getMessage(), exception);
            throw exception;
        }
    }
}
