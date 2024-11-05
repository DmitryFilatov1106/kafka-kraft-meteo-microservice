package ru.fildv.kafkameteostoremicroservice.service;

import ru.fildv.kafkameteocore.model.IndicatorEntity;
import ru.fildv.kafkameteocore.model.MeteoType;
import ru.fildv.kafkameteostoremicroservice.model.Summary;
import ru.fildv.kafkameteostoremicroservice.model.SummaryType;

import java.util.Set;

public interface SummaryService {
    Summary get(Long meteoId,
                Set<MeteoType> meteoTypes,
                Set<SummaryType> summaryTypes);

    void handle(IndicatorEntity indicatorEntity);
}
