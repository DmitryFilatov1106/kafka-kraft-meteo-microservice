package ru.fildv.kafkameteogeneratormicroservice.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import java.time.Duration;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaConfig {
    @Value("${myapp.topic.temperature}")
    private String topicTemperature;

    @Value("${myapp.topic.humidity}")
    private String topicHumidity;

    @Value("${myapp.topic.pressure}")
    private String topicPressure;

    private final String minInsyncReplicas = "2";
    private final int partitions = 3;
    private final int replicas = 3;
    private final int retentionDays = 7;

    @Bean
    public NewTopic temperatureTopic() {
        return TopicBuilder
                .name(topicTemperature)
                .partitions(partitions)
                .replicas(replicas)
                .configs(Map.of(
                                "min.insync.replicas",
                        minInsyncReplicas,
                                TopicConfig.RETENTION_MS_CONFIG,
                                String.valueOf(
                                        Duration.ofDays(retentionDays)
                                                .toMillis())
                        )
                )
                .build();
    }

    @Bean
    public NewTopic humidityTopic() {
        return TopicBuilder
                .name(topicHumidity)
                .partitions(partitions)
                .replicas(replicas)
                .configs(Map.of(
                                "min.insync.replicas",
                        minInsyncReplicas,
                                TopicConfig.RETENTION_MS_CONFIG,
                                String.valueOf(
                                        Duration.ofDays(retentionDays)
                                                .toMillis())
                        )
                )
                .build();
    }

    @Bean
    public NewTopic pressureTopic() {
        return TopicBuilder
                .name(topicPressure)
                .partitions(partitions)
                .replicas(replicas)
                .configs(Map.of(
                                "min.insync.replicas",
                        minInsyncReplicas,
                                TopicConfig.RETENTION_MS_CONFIG,
                                String.valueOf(
                                        Duration.ofDays(retentionDays)
                                                .toMillis())
                        )
                )
                .build();
    }
}
