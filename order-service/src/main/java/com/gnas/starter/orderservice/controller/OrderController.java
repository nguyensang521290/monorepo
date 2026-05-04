package com.gnas.starter.orderservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gnas.starter.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @PostMapping("/v1/order")
    public ResponseEntity<?> processOrder(@RequestBody OrderRequest request) throws JsonProcessingException {
        service.order(request);
        return ResponseEntity.ok(null);
    }
}
