package ru.fildv.kafkameteoservermicroservice.database.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.fildv.kafkameteocore.model.MeteoType;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "indicator")
public class IndicatorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "meteo_id")
    private Long meteoId;    // meteo appliance

    private LocalDateTime timestamp;
    private double value;

    @Column(name = "type")
    @Enumerated(value = EnumType.STRING)
    private MeteoType meteoType;
}
