package ru.fildv.kafkameteoservermicroservice.database.mapper;

import org.mapstruct.Mapper;
import ru.fildv.kafkameteocore.model.Indicator;
import ru.fildv.kafkameteocore.model.Mappable;
import ru.fildv.kafkameteoservermicroservice.database.entity.IndicatorEntity;

@Mapper(componentModel = "spring")
public interface IndicatorMapperDb
        extends Mappable<Indicator, IndicatorEntity> {
}
