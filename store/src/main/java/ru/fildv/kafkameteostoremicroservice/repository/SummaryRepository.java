package ru.fildv.kafkameteostoremicroservice.repository;

import ru.fildv.kafkameteocore.model.IndicatorEntity;
import ru.fildv.kafkameteocore.model.MeteoType;
import ru.fildv.kafkameteostoremicroservice.model.Summary;
import ru.fildv.kafkameteostoremicroservice.model.SummaryType;

import java.util.Optional;
import java.util.Set;

public interface SummaryRepository {
    Optional<Summary> findByMeteoId(long meteoId,
                                    Set<MeteoType> meteoTypes,
                                    Set<SummaryType> summaryTypes);

    void handle(IndicatorEntity indicatorEntity);
}
