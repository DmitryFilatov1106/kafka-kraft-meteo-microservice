package ru.fildv.kafkameteogeneratormicroservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import ru.fildv.kafkameteocore.model.Indicator;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaIndicatorServiceImpl implements KafkaIndicatorService {
    private final KafkaTemplate<String, Indicator> kafkaTemplate;

    @Override
    public void send(final Indicator indicator) {
        String topic = switch (indicator.getMeteoType()) {
            case TEMPERATURE -> "indicator-temperature";
            case HUMIDITY -> "indicator-humidity";
            case PRESSURE -> "indicator-pressure";
        };
        String key = String.valueOf(indicator.hashCode());
        CompletableFuture<SendResult<String, Indicator>> future = kafkaTemplate
                .send(topic, key, indicator);
        future.whenComplete((result, exception) -> {
            if (exception != null) {
                log.error("Failed to send indicator: {}",
                        exception.getMessage());
            } else {
                log.info("Message sent successfully: {}",
                        result.getRecordMetadata());
                log.info("Topic: {}", result.getRecordMetadata().topic());
                log.info("Partition: {}",
                        result.getRecordMetadata().partition());
                log.info("Offset: {}", result.getRecordMetadata().offset());
            }
        });
    }
}
