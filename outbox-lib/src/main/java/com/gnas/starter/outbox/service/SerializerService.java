package com.gnas.starter.outbox.service;

import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@Data
public class SerializerService {
    @Value("${spring.kafka.properties.schema.registry.url}")
    private String schemaRegistryUrl;

    @Value("${spring.kafka.properties.auto.register.schemas:false}")
    private boolean autoRegisterSchemas;

    @Value("${spring.kafka.properties.key.subject.name.strategy}")
    private String keySubjectNameStrategy;

    @Value("${spring.kafka.properties.value.subject.name.strategy}")
    private String valueSubjectNameStrategy;

    private KafkaAvroSerializer kafkaValueAvroSerializer;
    private KafkaAvroSerializer kafkaKeyAvroSerializer;
    private KafkaAvroDeserializer kafkaAvroDeserializer;

    @PostConstruct
    public void init() {
        Map<String, Object> config = new HashMap<>();
        config.put(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryUrl);
        config.put(AbstractKafkaSchemaSerDeConfig.AUTO_REGISTER_SCHEMAS, autoRegisterSchemas);
        config.put(AbstractKafkaSchemaSerDeConfig.KEY_SUBJECT_NAME_STRATEGY, keySubjectNameStrategy);
        config.put(AbstractKafkaSchemaSerDeConfig.VALUE_SUBJECT_NAME_STRATEGY, valueSubjectNameStrategy);

        kafkaKeyAvroSerializer = new KafkaAvroSerializer();
        kafkaKeyAvroSerializer.configure(config, true);

        kafkaValueAvroSerializer = new KafkaAvroSerializer();
        kafkaValueAvroSerializer.configure(config, false);

        kafkaAvroDeserializer = new KafkaAvroDeserializer();
        kafkaAvroDeserializer.configure(config, false);
    }

    public byte[] serializeValue(String topic, Object object) {
        log.debug("Serialize value object: {}", object);
        return kafkaValueAvroSerializer.serialize(topic, object);
    }

    public byte[] serializeKey(String topic, Object object) {
        log.debug("Serialize key object: {}", object);
        return kafkaKeyAvroSerializer.serialize(topic, object);
    }

    public Object deserialize(String topic, byte[] payload) {
        log.debug("message=\"Deserializing byte array in topic = {}\"", topic);
        return kafkaAvroDeserializer.deserialize(topic, payload);
    }
}
