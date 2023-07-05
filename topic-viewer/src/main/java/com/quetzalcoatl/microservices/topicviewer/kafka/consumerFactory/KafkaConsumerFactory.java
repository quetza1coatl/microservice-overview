package com.quetzalcoatl.microservices.topicviewer.kafka.consumerFactory;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import java.util.List;

public interface KafkaConsumerFactory {
    KafkaConsumer createConsumer();
    List<TopicPartition> getPartitionList();
    String getTopicName();
}
