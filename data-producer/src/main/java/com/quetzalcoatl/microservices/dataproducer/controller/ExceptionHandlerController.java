package com.quetzalcoatl.microservices.dataproducer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.lang.invoke.MethodHandles;


@RestControllerAdvice
public class ExceptionHandlerController {
    public static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public void handleConversionProblem(HttpMessageNotReadableException error){
        logger.error(String.format("Cause: [%s]. Details:[%s]", error.getClass().getSimpleName(), error.getCause().getLocalizedMessage()));
    }
}
