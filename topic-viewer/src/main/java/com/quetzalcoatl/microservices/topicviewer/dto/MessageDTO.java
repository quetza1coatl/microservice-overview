package com.quetzalcoatl.microservices.topicviewer.dto;

import lombok.Data;

/**
 * The class is used to transfer any topic data to the client side for displaying. So the key and value shouldn't be parametrized,
 * using a simple string representation will be enough.
 */
@Data
public class MessageDTO {
    private final String key;
    private final String value;
    private final String topic;
    private final Integer partition;
    private final Long offset;
}
