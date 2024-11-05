package ru.fildv.kafkameteostoremicroservice.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.fildv.kafkameteocore.model.MeteoType;
import ru.fildv.kafkameteostoremicroservice.model.Summary;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class SummaryDto {
    private long meteoId;
    private Map<MeteoType, List<Summary.SummaryEntry>> values;
}
