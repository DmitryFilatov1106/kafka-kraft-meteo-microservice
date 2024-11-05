package ru.fildv.kafkameteoservermicroservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.fildv.kafkameteocore.model.Indicator;
import ru.fildv.kafkameteoservermicroservice.database.entity.IndicatorEntity;
import ru.fildv.kafkameteoservermicroservice.database.mapper.IndicatorMapperDb;
import ru.fildv.kafkameteoservermicroservice.database.repository.IndicatorRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaIndicatorServiceImpl implements KafkaIndicatorService {
    private final IndicatorRepository indicatorRepository;
    private final IndicatorMapperDb indicatorMapperDb;

    private final String topicTemperature = "indicator-temperature";
    private final String topicHumidity = "indicator-humidity";
    private final String topicPressure = "indicator-pressure";

    @KafkaListener(topics = {
            topicTemperature,
            topicHumidity,
            topicPressure})
    @Override
    public void handle(final Indicator indicator) {

        log.info("Indicator was received from kafka: {}", indicator);
        IndicatorEntity indicatorEntity = indicatorMapperDb.toDto(indicator);

        var indicatorEntityRepo = indicatorRepository.save(indicatorEntity);
        log.info("Indicator was saved to repository: {}", indicatorEntityRepo);
    }
}
