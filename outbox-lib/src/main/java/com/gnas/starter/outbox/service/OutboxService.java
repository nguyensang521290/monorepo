package com.gnas.starter.outbox.service;

import com.gnas.starter.outbox.domain.OutboxEvent;
import com.gnas.starter.outbox.repository.OutboxEventJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OutboxService {
    private final OutboxEventJpaRepository outboxEventJpaRepository;
    private final SerializerService serializerService;

    @Transactional
    public void pushEventToTable(String topic, Object key, Object payload, String eventType) {
        log.debug("Pushing event to outbox table: topic={}, eventType={}", topic, eventType);
        
        byte[] serializedKey = serializerService.serializeKey(topic, key);
        byte[] serializedPayload = serializerService.serializeValue(topic, payload);

        OutboxEvent event = OutboxEvent.builder()
                .topic(topic)
                .key(serializedKey)
                .payload(serializedPayload)
                .eventType(eventType)
                .isPublished(false)
                .build();

        outboxEventJpaRepository.save(event);
        log.info("Successfully pushed event {} to outbox table", eventType);
    }
}
