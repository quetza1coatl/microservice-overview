package com.quetzalcoatl.microservices.dataproducer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.lang.invoke.MethodHandles;
import java.util.stream.Collectors;


@RestControllerAdvice
public class ExceptionHandlerController {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public void handleConversionProblem(HttpServletRequest request, HttpMessageNotReadableException error){
        String sensorIdHeader = request.getHeader(Controller.SENSOR_ID_HEADER);
        String sensorId = sensorIdHeader == null? "N/A" : sensorIdHeader;
         logger.error(String.format(
                "Message from sensor [%s] can't be read. Cause: [%s]. Details:[%s]",
                sensorId, error.getClass().getSimpleName(), error.getCause().getLocalizedMessage()
                ));
    }

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public void handleValidation(HttpServletRequest request, MethodArgumentNotValidException error){
        String sensorIdHeader = request.getHeader(Controller.SENSOR_ID_HEADER);
        String sensorId = sensorIdHeader == null? "N/A" : sensorIdHeader;

        String result = error.getBindingResult().getFieldErrors().stream()
                .filter(fe -> fe.getDefaultMessage() != null)
                .map(fe -> fe.getDefaultMessage().startsWith(fe.getField()) ?
                        fe.getDefaultMessage() : fe.getField() + ": " + fe.getDefaultMessage())
                .collect(Collectors.joining(", "));

        logger.error(String.format(
                "Data from sensor [%s] has a validation issue: [%s]",
                sensorId, result
        ));
    }
}
