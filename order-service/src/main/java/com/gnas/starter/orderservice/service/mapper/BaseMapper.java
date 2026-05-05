package com.gnas.starter.orderservice.service.mapper;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class BaseMapper {
    public String map(UUID value) {
        return value != null ? value.toString() : null;
    }

    public String map(LocalDateTime value) {
        return value != null ? value.toString() : LocalDateTime.now().toString();
    }
}
