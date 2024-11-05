package ru.fildv.kafkameteostoremicroservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.fildv.kafkameteocore.model.MeteoType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
public class Summary {
    private long meteoId;
    private final Map<MeteoType, List<SummaryEntry>> values = new HashMap<>();

    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class SummaryEntry {
        private SummaryType type;
        private double value;
        private long counter;
    }

    public void addValue(final MeteoType type, final SummaryEntry value) {
        if (values.containsKey(type)) {
            List<SummaryEntry> entries = new ArrayList<>(values.get(type));
            entries.add(value);
            values.put(type, entries);
        } else {
            values.put(type, List.of(value));
        }
    }
}
