package com.gnas.starter.outbox.service;

import com.gnas.starter.outbox.domain.OutboxEvent;
import com.gnas.starter.outbox.repository.OutboxEventJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class OutboxPublisher {
    private final OutboxEventJpaRepository repository;
    private final KafkaTemplate<byte[], byte[]> producer;

    @Scheduled(
            fixedDelayString = "${outbox.publisher.fixed-delay:5000}",
            initialDelayString = "0"
    )
    @SchedulerLock(
            name = "outbox_publisher_lock",
            lockAtMostFor = "${outbox.publisher.lock-at-most-for:30s}",
            lockAtLeastFor = "${outbox.publisher.lock-at-least-for:2s}"
    )
    public void publish() {
        LocalDateTime startTime = LocalDateTime.now();
        log.info("OutboxPublisher::publish start scheduler at {}", startTime);

        List<OutboxEvent> events = repository.findTop50ByIsPublishedFalseOrderByCreatedAtAsc();

        for (OutboxEvent event : events) {
            try {
                var record = new ProducerRecord<>(event.getTopic(), event.getKey(), event.getPayload());
                producer.send(record);

                event.setIsPublished(true);
                log.info("OutboxPublisher::publish successfully published event {} to topic {}", event.getId(), event.getTopic());
            } catch (Exception exception) {
                log.error("OutboxPublisher::publish Failed to publish event {}: {}", event.getId(), exception.getMessage(), exception);
                event.setIsPublished(false);
            }
        }

        repository.saveAll(events);

        LocalDateTime endTime = LocalDateTime.now();
        log.info("OutboxPublisher::publish end scheduler at {}", endTime);
    }
}
