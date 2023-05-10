package com.quetzalcoatl.microservices.dataproducer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.lang.invoke.MethodHandles;
import java.util.UUID;

@Service
public class ProducerService {
    public static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Value("${custom.kafka.main-topic.name}")
    private String topicName;
    private final KafkaTemplate<UUID, BlackHoleData> blackHoleKafkaTemplate;

    public ProducerService(KafkaTemplate<UUID, BlackHoleData> blackHoleKafkaTemplate) {
        this.blackHoleKafkaTemplate = blackHoleKafkaTemplate;
    }

    public void publishData(BlackHoleData data){
        ListenableFuture<SendResult<UUID, BlackHoleData>> futureResult = blackHoleKafkaTemplate.send(
                topicName, data.getDataId(), data
        );
        futureResult.addCallback(new ListenableFutureCallback<SendResult<UUID, BlackHoleData>>() {
            @Override
            public void onFailure(Throwable ex) {
                logger.error("Error during sending message with key: " + data.getDataId(), ex.getCause());
            }

            @Override
            public void onSuccess(SendResult<UUID, BlackHoleData> result) {
                logger.info("Data with key [{}] successfully sent to partition [{}] with offset [{}]",
                        result.getProducerRecord().key(), result.getRecordMetadata().partition(),
                        result.getRecordMetadata().offset());
            }
        });
    }
}
