package com.quetzalcoatl.microservices.dataproducer.kafka;

import com.quetzalcoatl.microservices.avro.BlackHoleData;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.UUIDSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Configuration
public class KafkaProducerConfig {
    @Value("${SPRING_KAFKA_BOOTSTRAP-SERVERS}")
    private String bootstrapAddress;
    @Value("${SCHEMA_REGISTRY_URL}")
    private String schemaRegistryUrl;

    @Bean
    public Map<String, Object> producerConfigs(){
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        config.put("schema.registry.url", schemaRegistryUrl);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, UUIDSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
        return config;
    }

    @Bean
    public ProducerFactory<UUID, BlackHoleData> blackHoleProducerFactory(){
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<UUID, BlackHoleData> blackHoleKafkaTemplate(){
        return new KafkaTemplate<>(blackHoleProducerFactory());
    }
}
