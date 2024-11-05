package ru.fildv.kafkameteostoremicroservice.web.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.fildv.kafkameteostoremicroservice.model.exception.IndicatorNotFoundException;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(IndicatorNotFoundException.class)
    public String indicatorNotFound(final IndicatorNotFoundException e) {
        return "Indicator not found.";
    }

    @ExceptionHandler
    public String server(final Exception e) {
        e.printStackTrace();
        return "Bad thing happened for server.";
    }

}
