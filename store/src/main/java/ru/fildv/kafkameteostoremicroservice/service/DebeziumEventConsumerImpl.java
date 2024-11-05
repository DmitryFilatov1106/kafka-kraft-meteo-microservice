package ru.fildv.kafkameteostoremicroservice.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.fildv.kafkameteocore.model.IndicatorEntity;
import ru.fildv.kafkameteocore.model.MeteoType;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

@Slf4j
@Service
@RequiredArgsConstructor
public class DebeziumEventConsumerImpl implements CDCEventConsumer {
    private final SummaryService summaryService;

    @KafkaListener(topics = "indicator")
    public void handle(final String message) {
        try {
            log.info("From kafka was received: {}", message);
            JsonObject payload = JsonParser.parseString(message)
                    .getAsJsonObject()
                    .get("payload")
                    .getAsJsonObject();
            IndicatorEntity indicatorEntity = new IndicatorEntity();
            indicatorEntity.setId(payload.get("id").getAsLong());
            indicatorEntity.setMeteoId(payload.get("meteo_id").getAsLong());
            indicatorEntity.setValue(payload.get("value").getAsDouble());
            indicatorEntity.setMeteoType(MeteoType.valueOf(
                    payload.get("type").getAsString()));
            indicatorEntity.setTimestamp(LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(
                            payload.get("timestamp").getAsLong() / 1000),
                    TimeZone.getDefault().toZoneId())
            );
            summaryService.handle(indicatorEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
