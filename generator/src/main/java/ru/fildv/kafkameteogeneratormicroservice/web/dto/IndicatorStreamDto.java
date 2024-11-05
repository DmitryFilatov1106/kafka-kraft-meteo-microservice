package ru.fildv.kafkameteogeneratormicroservice.web.dto;

import ru.fildv.kafkameteocore.model.MeteoType;

public record IndicatorStreamDto(int delayInMilliseconds,
                                 int count,
                                 MeteoType[] meteoTypes) {
}
