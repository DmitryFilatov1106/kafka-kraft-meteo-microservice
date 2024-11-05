package ru.fildv.kafkameteogeneratormicroservice.web.mapper;

import org.mapstruct.Mapper;
import ru.fildv.kafkameteocore.model.Indicator;
import ru.fildv.kafkameteogeneratormicroservice.web.dto.IndicatorDto;

@Mapper(componentModel = "spring")
public interface IndicatorMapperWeb extends Mappable<Indicator, IndicatorDto> {
}
