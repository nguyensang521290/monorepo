package com.gnas.starter.orderservice.config;

import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class AvroSerializerConfig {

    @Value("${spring.kafka.properties.schema.registry.url}")
    private String schemaRegistryUrl;

    @Value("${spring.kafka.producer.properties.auto.register.schemas:false}")
    private boolean autoRegisterSchemas;

    @Value("${spring.kafka.producer.properties.key.subject.name.strategy}")
    private String keySubjectNameStrategy;

    @Value("${spring.kafka.producer.properties.value.subject.name.strategy}")
    private String valueSubjectNameStrategy;

    private Map<String, Object> baseConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryUrl);
        config.put(AbstractKafkaSchemaSerDeConfig.AUTO_REGISTER_SCHEMAS, autoRegisterSchemas);
        return config;
    }

    @Bean(name = "keySerializer")
    public KafkaAvroSerializer keySerializer() {
        KafkaAvroSerializer serializer = new KafkaAvroSerializer();
        Map<String, Object> config = baseConfig();
        config.put(AbstractKafkaSchemaSerDeConfig.KEY_SUBJECT_NAME_STRATEGY, keySubjectNameStrategy);
        serializer.configure(config, true);
        return serializer;
    }

    @Bean(name = "valueSerializer")
    public KafkaAvroSerializer valueSerializer() {
        KafkaAvroSerializer serializer = new KafkaAvroSerializer();
        Map<String, Object> config = baseConfig();
        config.put(AbstractKafkaSchemaSerDeConfig.VALUE_SUBJECT_NAME_STRATEGY, valueSubjectNameStrategy);
        serializer.configure(config, false);
        return serializer;
    }
}