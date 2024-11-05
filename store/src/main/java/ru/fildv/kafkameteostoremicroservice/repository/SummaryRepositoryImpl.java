package ru.fildv.kafkameteostoremicroservice.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import ru.fildv.kafkameteocore.model.IndicatorEntity;
import ru.fildv.kafkameteocore.model.MeteoType;
import ru.fildv.kafkameteostoremicroservice.config.RedisSchema;
import ru.fildv.kafkameteostoremicroservice.model.Summary;
import ru.fildv.kafkameteostoremicroservice.model.SummaryType;

import java.util.Optional;
import java.util.Set;

@Slf4j
@Repository
@RequiredArgsConstructor
public class SummaryRepositoryImpl implements SummaryRepository {
    private final JedisPool jedisPool;

    @Override
    public Optional<Summary> findByMeteoId(final long meteoId,
                             final Set<MeteoType> meteoTypes,
                             final Set<SummaryType> summaryTypes) {
        try (Jedis jedis = jedisPool.getResource()) {
            if (!jedis.sismember(
                    RedisSchema.meteoKeys(), String.valueOf(meteoId))) {
                return Optional.empty();
            }
            if (meteoTypes.isEmpty() && !summaryTypes.isEmpty()) {
                return getSummary(
                        meteoId,
                        Set.of(MeteoType.values()),
                        summaryTypes,
                        jedis
                );
            } else if (!meteoTypes.isEmpty() && summaryTypes.isEmpty()) {
                return getSummary(
                        meteoId,
                        meteoTypes,
                        Set.of(SummaryType.values()),
                        jedis
                );
            } else {
                return getSummary(
                        meteoId,
                        meteoTypes,
                        summaryTypes,
                        jedis
                );
            }
        }
    }

    private Optional<Summary> getSummary(final long meteoId,
                                         final Set<MeteoType> meteoTypes,
                                         final Set<SummaryType> summaryTypes,
                                         final Jedis jedis) {
        Summary summary = new Summary();
        summary.setMeteoId(meteoId);
        for (MeteoType mType : meteoTypes) {
            for (SummaryType sType : summaryTypes) {
                Summary.SummaryEntry entry = new Summary.SummaryEntry();
                entry.setType(sType);
                String value = jedis
                        .hget(RedisSchema.summaryKey(meteoId, mType),
                                sType.name().toLowerCase());
                if (value != null) {
                    entry.setValue(Double.parseDouble(value));
                }
                String counter = jedis
                        .hget(RedisSchema.summaryKey(meteoId, mType),
                                "counter");
                if (counter != null) {
                    entry.setCounter(Long.parseLong(counter));
                }
                summary.addValue(mType, entry);
            }
        }
        return Optional.of(summary);
    }

    @Override
    public void handle(final IndicatorEntity indicatorEntity) {
        try (Jedis jedis = jedisPool.getResource()) {
            if (!jedis.sismember(
                    RedisSchema.meteoKeys(),
                    String.valueOf(indicatorEntity.getMeteoId())
            )) {
                jedis.sadd(
                        RedisSchema.meteoKeys(),
                        String.valueOf(indicatorEntity.getMeteoId())
                );
            }
            log.info("SummaryRepositoryImpl, indicator: {}", indicatorEntity);
            updateMinValue(indicatorEntity, jedis);
            updateMaxValue(indicatorEntity, jedis);
            updateSumAndAvgValue(indicatorEntity, jedis);
        }
    }

    private void updateMinValue(
            final IndicatorEntity indicatorEntity,
            final Jedis jedis) {
        String key = RedisSchema.summaryKey(
                indicatorEntity.getMeteoId(), indicatorEntity.getMeteoType());
        String value = jedis.hget(key, SummaryType.MIN.name().toLowerCase());
        if (value == null
            || indicatorEntity.getValue() < Double.parseDouble(value)) {
            jedis.hset(
                    key,
                    SummaryType.MIN.name().toLowerCase(),
                    String.valueOf(indicatorEntity.getValue())
            );
        }
    }

    private void updateMaxValue(
            final IndicatorEntity indicatorEntity,
            final Jedis jedis) {
        String key = RedisSchema.summaryKey(
                indicatorEntity.getMeteoId(),
                indicatorEntity.getMeteoType()
        );
        String value = jedis.hget(
                key,
                SummaryType.MAX.name().toLowerCase()
        );
        if (value == null
            || indicatorEntity.getValue() > Double.parseDouble(value)) {
            jedis.hset(
                    key,
                    SummaryType.MAX.name().toLowerCase(),
                    String.valueOf(indicatorEntity.getValue())
            );
        }
    }

    private void updateSumAndAvgValue(final IndicatorEntity indicatorEntity,
                                      final Jedis jedis) {
        updateSumValue(indicatorEntity, jedis);
        String key = RedisSchema.summaryKey(indicatorEntity.getMeteoId(),
                indicatorEntity.getMeteoType());
        String counter = jedis.hget(key, "counter");
        if (counter == null) {
            counter = String.valueOf(
                    jedis.hset(
                            key,
                            "counter",
                            String.valueOf(1)
                    )
            );
        } else {
            counter = String.valueOf(
                    jedis.hincrBy(
                            key,
                            "counter",
                            1
                    )
            );
        }
        String sum = jedis.hget(key, SummaryType.SUM.name().toLowerCase());
        jedis.hset(key, SummaryType.AVG.name().toLowerCase(),
                String.valueOf(
                        Double.parseDouble(sum)
                        / Double.parseDouble(counter)));
    }

    private void updateSumValue(
            final IndicatorEntity indicatorEntity,
            final Jedis jedis) {
        String key = RedisSchema.summaryKey(
                indicatorEntity.getMeteoId(),
                indicatorEntity.getMeteoType()
        );
        String value = jedis.hget(
                key,
                SummaryType.SUM.name().toLowerCase()
        );
        if (value == null) {
            jedis.hset(
                    key,
                    SummaryType.SUM.name().toLowerCase(),
                    String.valueOf(indicatorEntity.getValue())
            );
        } else {
            jedis.hincrByFloat(
                    key,
                    SummaryType.SUM.name().toLowerCase(),
                    indicatorEntity.getValue()
            );
        }
    }
}
