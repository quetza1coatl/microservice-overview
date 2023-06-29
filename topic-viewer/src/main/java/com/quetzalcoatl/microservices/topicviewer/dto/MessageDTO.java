package com.quetzalcoatl.microservices.topicviewer.dto;

import lombok.Data;

@Data
public class MessageDTO<K, V> {
    private final K key;
    private final V value;
    private final String topic;
    private final Integer partition;
    private final Long offset;
}
