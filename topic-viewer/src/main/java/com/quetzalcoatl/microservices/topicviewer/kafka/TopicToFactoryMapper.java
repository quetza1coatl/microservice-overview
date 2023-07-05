package com.quetzalcoatl.microservices.topicviewer.kafka;

import com.quetzalcoatl.microservices.topicviewer.kafka.consumerFactory.KafkaConsumerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class TopicToFactoryMapper {
    @Autowired
    private List<KafkaConsumerFactory> kafkaConsumerFactories;

    @Bean
    public Map<String, KafkaConsumerPartitionListPair> kafkaConsumerPartitionsMap() {
        Map<String, KafkaConsumerPartitionListPair> map = new HashMap<>();
        for (KafkaConsumerFactory factory : kafkaConsumerFactories) {
            String topic = factory.getTopicName();
            KafkaConsumerPartitionListPair pair = new KafkaConsumerPartitionListPair(
                    factory.createConsumer(), factory.getPartitionList()
            );
            map.put(topic, pair);
        }
        return map;
    }


}
