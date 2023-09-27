package com.quetzalcoatl.microservices.topicviewer.service;

import com.quetzalcoatl.microservices.topicviewer.dto.MessageDTO;
import com.quetzalcoatl.microservices.topicviewer.kafka.KafkaConsumerPartitionListPair;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TopicViewerService {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    @Autowired
    Map<String, KafkaConsumerPartitionListPair> kafkaConsumerPartitionsMap;

    public boolean checkIfTopicExist(String topicName){
        return kafkaConsumerPartitionsMap.containsKey(topicName);
    }

    /**
     *
     * @param topicName topic which will be read for getting messages
     * @return list of data extracted from specified topic. If topic doesn't exist returns empty list
     */
    @SuppressWarnings("rawtypes")
    public List<MessageDTO> extractMessages(String topicName) {
        final long INITIAL_OFFSET = 0L;
        final int POLL_DURATION = 1000;

        List<MessageDTO> result = new ArrayList<>();
        KafkaConsumerPartitionListPair entry = kafkaConsumerPartitionsMap.get(topicName);
        if(entry == null){
            return result;
        }
        KafkaConsumer consumer = entry.getConsumer();
        List<TopicPartition> partitionList = entry.getPartitionList();
        partitionList.forEach(p -> consumer.seek(p, INITIAL_OFFSET));

        try{
            ConsumerRecords records = consumer.poll(Duration.ofMillis(POLL_DURATION));
            for (Object objRecord : records){
                ConsumerRecord record = (ConsumerRecord) objRecord;
                result.add(new MessageDTO(record.key().toString(), record.value().toString(), record.topic(), record.partition(), record.offset()));
            }

        }catch(Exception ex){
            logger.error(String.format("Unable to get messages from existing topic %s", topicName), ex);
        }
        return result;
    }
}
