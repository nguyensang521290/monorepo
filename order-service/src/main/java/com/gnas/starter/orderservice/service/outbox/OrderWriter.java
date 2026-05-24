package com.gnas.starter.orderservice.service.outbox;

import com.gnas.starter.orderservice.domain.OrderData;
import com.gnas.starter.orderservice.service.mapper.OrderMapper;
import com.gnas.starter.outbox.service.OutboxService;
import org.springframework.stereotype.Service;

@Service
public class OrderWriter extends AbstractOutboxWriter<OrderData> {
    private final OrderMapper orderMapper;
    private static final String ORDER_TOPIC = "order-topic";

    public OrderWriter(OutboxService outboxService, OrderMapper orderMapper) {
        super(outboxService);
        this.orderMapper = orderMapper;
    }

    @Override
    public String getType() {
        return "order__writer";
    }

    @Override
    protected OutboxMapper<OrderData> getMapper() {
        return orderMapper;
    }

    @Override
    protected String getTopic() {
        return ORDER_TOPIC;
    }
}
