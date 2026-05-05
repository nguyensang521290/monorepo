package com.gnas.starter.orderservice.service.mapper;

import com.gnas.order.OrderCreatedEvent;
import com.gnas.order.OrderIdKey;
import com.gnas.starter.orderservice.domain.OrderData;
import com.gnas.starter.orderservice.service.outbox.OutboxMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class OrderMapper extends BaseMapper implements OutboxMapper<OrderData> {

    @Override
    public abstract OrderIdKey toKey(OrderData input);

    @Override
    public abstract OrderCreatedEvent toPayload(OrderData input);
}
