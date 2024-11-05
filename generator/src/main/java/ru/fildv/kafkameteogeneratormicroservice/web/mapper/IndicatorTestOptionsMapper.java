package ru.fildv.kafkameteogeneratormicroservice.web.mapper;

import org.mapstruct.Mapper;
import ru.fildv.kafkameteogeneratormicroservice.model.IndicatorTestOptions;
import ru.fildv.kafkameteogeneratormicroservice.web.dto.IndicatorStreamDto;

@Mapper(componentModel = "spring")
public interface IndicatorTestOptionsMapper
        extends Mappable<IndicatorTestOptions, IndicatorStreamDto> {
}
