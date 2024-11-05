package ru.fildv.kafkameteogeneratormicroservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.fildv.kafkameteocore.model.MeteoType;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class IndicatorTestOptions {
    private int delayInMilliseconds;
    private int count;
    private MeteoType[] meteoTypes;
}
