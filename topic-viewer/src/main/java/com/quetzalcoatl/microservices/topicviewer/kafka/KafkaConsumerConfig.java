package com.quetzalcoatl.microservices.topicviewer.kafka;

import com.quetzalcoatl.microservices.dataproducer.model.BlackHoleData;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.UUIDSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.*;

@Configuration
public class KafkaConsumerConfig {
    @Value("${SPRING_KAFKA_CONSUMER_BOOTSTRAP-SERVERS}")
    private String bootstrapAddress;

    @Value("${TOPIC_BH_SENSORS_EVENT_NAME}")
    private String blackHoleDataTopic;
    @Value("${TOPIC_BH_SENSORS_EVENT_PARTITIONS}")
    private Integer blackHoleDataPartitions;

    @Bean
    public Map<String, Object> viewerConsumerConfigs(){
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, UUIDSerializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return config;
    }

    @Bean
    public KafkaConsumer<UUID, BlackHoleData> kafkaConsumerBlackHoleData(Map<String, Object> viewerConsumerConfigs){
        return new KafkaConsumer<>(viewerConsumerConfigs);
    }

    @Bean
    public List<TopicPartition> BlackHoleDataPartitions(){
        List<TopicPartition> partitions = new ArrayList<>();
        for (int i = 0; i < blackHoleDataPartitions; i++){
            TopicPartition partition = new TopicPartition(blackHoleDataTopic, i);
            partitions.add(partition);
        }
        return partitions;
    }
}
