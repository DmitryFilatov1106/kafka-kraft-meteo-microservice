package ru.fildv.kafkameteogeneratormicroservice.service;

import ru.fildv.kafkameteogeneratormicroservice.model.IndicatorTestOptions;

public interface TestIndicatorService {
    void sendMessages(IndicatorTestOptions testOptions);
}
