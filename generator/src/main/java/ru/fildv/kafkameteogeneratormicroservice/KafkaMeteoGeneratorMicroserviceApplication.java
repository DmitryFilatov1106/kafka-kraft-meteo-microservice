package ru.fildv.kafkameteogeneratormicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaMeteoGeneratorMicroserviceApplication {

    public static void main(final String[] args) {
        SpringApplication.run(
                KafkaMeteoGeneratorMicroserviceApplication.class, args);
    }

}
