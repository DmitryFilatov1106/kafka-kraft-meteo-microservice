package ru.fildv.kafkameteostoremicroservice.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.fildv.kafkameteocore.model.MeteoType;
import ru.fildv.kafkameteostoremicroservice.model.Summary;
import ru.fildv.kafkameteostoremicroservice.model.SummaryType;
import ru.fildv.kafkameteostoremicroservice.service.SummaryService;
import ru.fildv.kafkameteostoremicroservice.web.dto.SummaryDto;
import ru.fildv.kafkameteostoremicroservice.web.mapper.SummaryMapper;

import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/api/v1/analytics")
@RequiredArgsConstructor
public class AnalyticsController {
    private final SummaryService summaryService;
    private final SummaryMapper summaryMapper;

    @GetMapping("/summary/{meteoId}")
    public SummaryDto getSummary(final @PathVariable
                                 long meteoId,

                                 final @RequestParam(value = "mt",
                                         required = false)
                                 Set<MeteoType> meteoTypes,

                                 final @RequestParam(value = "st",
                                         required = false)
                                 Set<SummaryType> summaryTypes) {
        log.info("meteoId: {}", meteoId);
        Summary summary = summaryService.get(meteoId, meteoTypes, summaryTypes);
        return summaryMapper.toDto(summary);
    }
}
