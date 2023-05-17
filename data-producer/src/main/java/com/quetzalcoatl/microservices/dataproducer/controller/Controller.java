package com.quetzalcoatl.microservices.dataproducer.controller;

import com.quetzalcoatl.microservices.dataproducer.ProducerService;
import com.quetzalcoatl.microservices.dataproducer.model.BlackHoleDataDTO;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class Controller {
    private final ProducerService producerService;

    public Controller(ProducerService producerService) {
        this.producerService = producerService;
    }

    @PostMapping(value = "/postData", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addData(@Valid @RequestBody BlackHoleDataDTO dto, BindingResult bindingResult){
        System.out.println(dto);
    }

}
