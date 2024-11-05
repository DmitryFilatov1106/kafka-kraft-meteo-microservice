package ru.fildv.kafkameteostoremicroservice.config;

import lombok.Setter;
import lombok.experimental.UtilityClass;

import java.util.Objects;

@UtilityClass
public class KeyHelper {
    private static final String DEFAULT_PREFIX = "app";

    @Setter
    private String prefix = null;

    public String getKey(final String key) {
        return getPrefix() + ":" + key;
    }

    public String getPrefix() {
        return Objects.requireNonNullElse(prefix, DEFAULT_PREFIX);
    }
}
