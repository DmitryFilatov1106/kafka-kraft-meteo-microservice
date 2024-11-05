package ru.fildv.kafkameteoservermicroservice.service;

import ru.fildv.kafkameteocore.model.Indicator;

public interface KafkaIndicatorService {
    void handle(Indicator indicator);
}
