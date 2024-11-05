package ru.fildv.kafkameteoservermicroservice.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.fildv.kafkameteoservermicroservice.database.entity.IndicatorEntity;

public interface IndicatorRepository
        extends JpaRepository<IndicatorEntity, Long> {
}
