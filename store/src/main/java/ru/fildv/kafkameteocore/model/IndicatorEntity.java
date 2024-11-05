package ru.fildv.kafkameteocore.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class IndicatorEntity {
    private Long id;
    private Long meteoId;    // meteo appliance
    private LocalDateTime timestamp;
    private double value;
    private MeteoType meteoType;
}
