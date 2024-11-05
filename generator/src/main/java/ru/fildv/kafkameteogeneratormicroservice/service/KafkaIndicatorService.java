package ru.fildv.kafkameteogeneratormicroservice.service;

import ru.fildv.kafkameteocore.model.Indicator;

public interface KafkaIndicatorService {
    void send(Indicator indicator);
}
