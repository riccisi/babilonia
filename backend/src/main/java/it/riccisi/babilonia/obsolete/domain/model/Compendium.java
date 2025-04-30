package it.riccisi.babilonia.obsolete.domain.model;

import it.riccisi.babilonia.domain.ItemType;
import it.riccisi.babilonia.domain.TranslationStatus;
import it.riccisi.babilonia.domain.model.CompendiumItem;
import it.riccisi.babilonia.domain.model.Mapping;
import it.riccisi.babilonia.domain.model.TranslationEntry;
import lombok.NonNull;

import java.util.List;
import java.util.Map;

public record Compendium(
    String id,
    String name,
    ItemType itemType,
    it.riccisi.babilonia.domain.model.Mapping mapping,
    String originalJson,
    List<TranslationEntry> translations
) {

    List<CompendiumItem> items(@NonNull String language, @NonNull Map<ItemType, it.riccisi.babilonia.domain.model.Mapping> defaultMappings) {
        final it.riccisi.babilonia.domain.model.Mapping base = defaultMappings.getOrDefault(this.itemType, new it.riccisi.babilonia.domain.model.Mapping(Map.of()));
        final it.riccisi.babilonia.domain.model.Mapping merged = this.mapping != null ? base.merge(mapping) : base;
        return CompendiumExtractor.extract(this, language, merged);
    }

    double progress(String language, @NonNull Map<ItemType, Mapping> defaultMappings) {
        List<CompendiumItem> items = this.items(language, defaultMappings);
        int total = items.stream().mapToInt(i -> i.properties().size()).sum();
        int translated = items.stream()
            .flatMap(i -> i.properties().stream())
            .filter(p -> p.status() == TranslationStatus.TRANSLATED)
            .toList()
            .size();
        return total == 0 ? 1.0 : (double) translated / total;
    }
}