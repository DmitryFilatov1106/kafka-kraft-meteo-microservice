package ru.fildv.kafkameteogeneratormicroservice.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import ru.fildv.kafkameteocore.model.Indicator;
import ru.fildv.kafkameteocore.model.MeteoType;
import ru.fildv.kafkameteogeneratormicroservice.model.IndicatorTestOptions;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Service
@RequiredArgsConstructor
public class TestIndicatorServiceImpl implements TestIndicatorService {
    private final ScheduledExecutorService executorService
            = Executors.newSingleThreadScheduledExecutor();
    private final KafkaIndicatorService kafkaDataService;

    @Override
    @SneakyThrows
    public void sendMessages(final IndicatorTestOptions testOptions) {
        if (testOptions.getMeteoTypes().length > 0
            && testOptions.getCount() > 0) {
            for (int i = 0; i < testOptions.getCount(); i++) {
                Indicator indicator = Indicator.builder()
                        .meteoId((long) getRandomNumber(1, 50))
                        .timestamp(LocalDateTime.now())
                        .meteoType(getRandomMeasurement(
                                testOptions.getMeteoTypes()))
                        .build();

                switch (indicator.getMeteoType()) {
                    case TEMPERATURE -> indicator
                            .setValue(getRandomNumber(-60, 60));
                    case HUMIDITY -> indicator
                            .setValue(getRandomNumber(1, 100));
                    case PRESSURE -> indicator
                            .setValue(getRandomNumber(637, 812));
                    default -> {
                        continue;
                    }
                }
                kafkaDataService.send(indicator);
                if (testOptions.getDelayInMilliseconds() > 0) {
                    Thread.sleep(testOptions.getDelayInMilliseconds());
                }
            }
        }
    }

    private double getRandomNumber(final int from, final int to) {
        return from + Math.random() * (to - from);
    }

    private MeteoType getRandomMeasurement(final MeteoType[] measurementTypes) {
        int randomTypeId = (int) (Math.random() * measurementTypes.length);
        return measurementTypes[randomTypeId];
    }
}
