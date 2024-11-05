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
public class Indicator {
    private long meteoId;    // meteo appliance
    private LocalDateTime timestamp;
    private double value;
    private MeteoType meteoType;
}
