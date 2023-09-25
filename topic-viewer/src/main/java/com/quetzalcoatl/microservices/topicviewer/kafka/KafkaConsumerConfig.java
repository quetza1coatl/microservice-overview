package com.quetzalcoatl.microservices.topicviewer.kafka;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.UUIDDeserializer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



import java.util.*;

@Configuration
public class KafkaConsumerConfig {
    @Value("${SPRING_KAFKA_BOOTSTRAP-SERVERS}")
    private String bootstrapAddress;

    @Value("${SCHEMA_REGISTRY_URL}")
    private String schemaRegistryUrl;

    @Bean("generalConsumerConfigs")
    public Properties generalConsumerConfigs(){
        Properties config = new Properties();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        config.put("schema.registry.url", schemaRegistryUrl);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, UUIDDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
        config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return config;
    }
}
