package com.gnas.starter.paymentservice.service;

import com.gnas.starter.paymentservice.domain.OrderPayment;
import com.gnas.starter.paymentservice.domain.OrderPaymentPrimaryKey;
import com.gnas.starter.paymentservice.domain.OrderPaymentStatus;
import com.gnas.starter.paymentservice.repository.OrderPaymentJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderPaymentService {

    private final OrderPaymentJpaRepository repository;

    public void processOrderPayment(UUID orderId, List<String> products) {
        if (products == null || products.isEmpty()) {
            throw new RuntimeException("Products MUST NOT empty");
        }

        OrderPayment record = OrderPayment.builder()
                .orderPaymentPrimaryKey(new OrderPaymentPrimaryKey(UUID.randomUUID(), orderId))
                .status(OrderPaymentStatus.CREATED)
                .products(products)
                .build();

        repository.save(record);

        log.info("OrderPaymentService::processOrderPayment Successfully process order payment id={}", record.getOrderPaymentPrimaryKey().getOrderId());
    }
}
