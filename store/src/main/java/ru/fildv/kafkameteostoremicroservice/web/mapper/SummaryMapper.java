package ru.fildv.kafkameteostoremicroservice.web.mapper;

import org.mapstruct.Mapper;
import ru.fildv.kafkameteocore.model.Mappable;
import ru.fildv.kafkameteostoremicroservice.model.Summary;
import ru.fildv.kafkameteostoremicroservice.web.dto.SummaryDto;

@Mapper(componentModel = "spring")
public interface SummaryMapper extends Mappable<Summary, SummaryDto> {
}
