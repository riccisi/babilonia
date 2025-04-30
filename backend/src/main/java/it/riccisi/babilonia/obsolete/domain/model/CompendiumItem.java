package it.riccisi.babilonia.obsolete.domain.model;

import it.riccisi.babilonia.domain.TranslationStatus;
import it.riccisi.babilonia.domain.model.TranslatableProperty;

import java.util.List;

public record CompendiumItem(
    String itemId,
    String name,
    String type,
    List<TranslatableProperty> properties
) {

    public double progress() {
        if (properties.isEmpty()) return 1.0;

        long translated = properties.stream()
            .filter(p -> p.status() == TranslationStatus.TRANSLATED)
            .count();

        return (double) translated / properties.size();
    }

    public boolean hasStatus(TranslationStatus status) {
        return properties.stream().anyMatch(p -> p.status() == status);
    }
}

