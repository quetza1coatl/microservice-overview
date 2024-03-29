package com.quetzalcoatl.microservices.topicviewer.kafka.consumerFactory;

import com.quetzalcoatl.microservices.avro.BlackHoleData;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class BlackHoleDataKafkaConsumer implements KafkaConsumerFactory{

    @Autowired
    @Qualifier("generalConsumerConfigs")
    private Properties generalConsumerConfigs;
    @Value("${TOPIC_BH_SENSORS_EVENT_NAME}")
    private String blackHoleDataTopic;
    @Value("${TOPIC_BH_SENSORS_EVENT_PARTITIONS}")
    private Integer blackHoleDataPartitions;

    @Override
    public KafkaConsumer<UUID, BlackHoleData> createConsumer() {
        KafkaConsumer<UUID, BlackHoleData> consumer = new KafkaConsumer<>(generalConsumerConfigs);
        consumer.assign(getPartitionList());
        return consumer;
    }

    @Override
    public List<TopicPartition> getPartitionList() {
        List<TopicPartition> partitions = new ArrayList<>();
        for (int i = 0; i < blackHoleDataPartitions; i++){
            TopicPartition partition = new TopicPartition(blackHoleDataTopic, i);
            partitions.add(partition);
        }
        return partitions;
    }

    @Override
    public String getTopicName() {
        return blackHoleDataTopic;
    }
}
