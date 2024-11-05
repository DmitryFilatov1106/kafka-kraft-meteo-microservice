package ru.fildv.kafkameteostoremicroservice.service;

public interface CDCEventConsumer {
    void handle(String message);
}
