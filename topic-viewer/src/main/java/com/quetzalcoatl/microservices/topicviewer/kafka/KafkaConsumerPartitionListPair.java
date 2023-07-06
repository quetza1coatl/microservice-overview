package com.quetzalcoatl.microservices.topicviewer.kafka;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class KafkaConsumerPartitionListPair {
    private KafkaConsumer consumer;
    private List<TopicPartition> partitionList;
}
