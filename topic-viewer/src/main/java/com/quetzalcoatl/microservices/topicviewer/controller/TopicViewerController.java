package com.quetzalcoatl.microservices.topicviewer.controller;

import com.quetzalcoatl.microservices.topicviewer.dto.MessageDTO;
import com.quetzalcoatl.microservices.topicviewer.service.TopicViewerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.lang.invoke.MethodHandles;
import java.util.List;

@RestController
@RequestMapping("/topics")
public class TopicViewerController {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final TopicViewerService service;

    public TopicViewerController(TopicViewerService service) {
        this.service = service;
    }

    @GetMapping(value = "/{topic}/fetch", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MessageDTO> getMessages(@PathVariable String topic) {
        if (!service.checkIfTopicExist(topic)) {
            logger.info("topic with name {} haven't been found", topic);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found");
        }
        return service.extractMessages(topic);


    }

}
