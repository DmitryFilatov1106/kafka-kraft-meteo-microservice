package ru.fildv.kafkameteogeneratormicroservice.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.fildv.kafkameteocore.model.Indicator;
import ru.fildv.kafkameteogeneratormicroservice.model.IndicatorTestOptions;
import ru.fildv.kafkameteogeneratormicroservice.service.KafkaIndicatorService;
import ru.fildv.kafkameteogeneratormicroservice.service.TestIndicatorService;
import ru.fildv.kafkameteogeneratormicroservice.web.dto.IndicatorDto;
import ru.fildv.kafkameteogeneratormicroservice.web.dto.IndicatorStreamDto;
import ru.fildv.kafkameteogeneratormicroservice.web.mapper.IndicatorMapperWeb;
import ru.fildv.kafkameteogeneratormicroservice.web.mapper.IndicatorTestOptionsMapper;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/v1/indicator")
public class IndicatorController {
    private final KafkaIndicatorService kafkaIndicatorService;
    private final TestIndicatorService testIndicatorService;

    private final IndicatorMapperWeb indicatorMapperWeb;
    private final IndicatorTestOptionsMapper indicatorTestOptionsMapper;

    @PostMapping("/send")
    public void send(final @RequestBody IndicatorDto indicatorDto) {
        log.info("Was received indicator DTO {}", indicatorDto);
        Indicator indicator = indicatorMapperWeb.toEntity(indicatorDto);
        log.info("It was mapped to indicator {}", indicator);
        kafkaIndicatorService.send(indicator);
    }

    @PostMapping("/test/send")
    public void testSend(final @RequestBody
                         IndicatorStreamDto testOptionsDto) {
        log.info("Was received options DTO {}", testOptionsDto);
        IndicatorTestOptions testOptions
                = indicatorTestOptionsMapper.toEntity(testOptionsDto);
        log.info("It was mapped to indicator test options {}", testOptions);
        testIndicatorService.sendMessages(testOptions);
    }
}
