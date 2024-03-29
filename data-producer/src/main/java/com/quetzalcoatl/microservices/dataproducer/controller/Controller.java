package com.quetzalcoatl.microservices.dataproducer.controller;

import com.quetzalcoatl.microservices.dataproducer.ProducerService;
import com.quetzalcoatl.microservices.avro.BlackHoleData;
import com.quetzalcoatl.microservices.dataproducer.model.BlackHoleDataDTO;
import com.quetzalcoatl.microservices.dataproducer.util.BlackHoleDataMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.invoke.MethodHandles;
import java.util.Objects;

@RestController
@RequestMapping("/aggregator")
public class Controller {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    final static String SENSOR_ID_HEADER = "Sensor-Id";
    private final ProducerService producerService;

    public Controller(ProducerService producerService) {
        this.producerService = producerService;
    }

    @PostMapping(value = "/postData", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addData(@Valid @RequestBody BlackHoleDataDTO dto, @RequestHeader(name = SENSOR_ID_HEADER) String sensorId) {
        if (!Objects.equals(sensorId, dto.getSensorId().name())) {
            logger.error(
                    "Sensor Id from header and body are different (header:[{}], body:[{}]). Skipping this data...",
                    sensorId, dto.getSensorId().name());
            return;
        }
        logger.info("DTO received: {}",dto);
        BlackHoleData blackHoleData = BlackHoleDataMapper.convertFromDto(dto);
        logger.info("DTO converted to entity: {}", blackHoleData);
        producerService.publishData(blackHoleData);
    }

}
