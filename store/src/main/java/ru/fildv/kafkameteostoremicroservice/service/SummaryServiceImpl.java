package ru.fildv.kafkameteostoremicroservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.fildv.kafkameteocore.model.IndicatorEntity;
import ru.fildv.kafkameteocore.model.MeteoType;
import ru.fildv.kafkameteostoremicroservice.model.Summary;
import ru.fildv.kafkameteostoremicroservice.model.SummaryType;
import ru.fildv.kafkameteostoremicroservice.model.exception.IndicatorNotFoundException;
import ru.fildv.kafkameteostoremicroservice.repository.SummaryRepository;

import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class SummaryServiceImpl implements SummaryService {

    private final SummaryRepository summaryRepository;

    @Override
    public Summary get(final Long meteoId,
                       final Set<MeteoType> meteoTypes,
                       final Set<SummaryType> summaryTypes) {
        return summaryRepository.findByMeteoId(
                        meteoId,
                        meteoTypes == null ? Set.of(MeteoType.values())
                                : meteoTypes,
                        summaryTypes == null ? Set.of(SummaryType.values())
                                : summaryTypes
                )
                .orElseThrow(IndicatorNotFoundException::new);
    }

    @Override
    public void handle(final IndicatorEntity indicatorEntity) {
        log.info("SummaryServiceImpl gets {}", indicatorEntity);
        summaryRepository.handle(indicatorEntity);
    }
}
