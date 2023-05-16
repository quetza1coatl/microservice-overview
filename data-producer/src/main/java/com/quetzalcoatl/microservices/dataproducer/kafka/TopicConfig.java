package com.quetzalcoatl.microservices.dataproducer.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicConfig {
    @Value("${custom.kafka.main-topic.name}")
    private String topicName;
    @Value("${custom.kafka.main-topic.partitions}")
    private Integer partitions;

    @Bean
    public NewTopic topic(){
        return new NewTopic(topicName, partitions, (short) 1);
    }
}
