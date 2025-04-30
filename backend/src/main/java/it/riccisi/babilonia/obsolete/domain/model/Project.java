package it.riccisi.babilonia.obsolete.domain.model;

import it.riccisi.babilonia.domain.ItemType;
import it.riccisi.babilonia.domain.model.Compendium;
import it.riccisi.babilonia.domain.model.CompendiumItem;
import it.riccisi.babilonia.domain.model.Mapping;

import java.util.List;
import java.util.Map;

public record Project(
    String id,
    String name,
    String description,
    String system,
    String world,
    Map<ItemType, Mapping> mappings,
    List<Compendium> compendiums
) {

    public double progress(final String lang) {
        if (this.compendiums.isEmpty()) return 1.0;
        return this.compendiums.stream()
            .mapToDouble(c -> c.progress(lang, mappings))
            .average()
            .orElse(1.0);
    }

    public List<CompendiumItem> compendiumItems(final String id, final String lang) {
        return this.compendiums.stream()
            .filter(c -> c.id().equals(id))
            .findFirst()
            .map(c -> c.items(lang, mappings))
            .orElse(List.of());
    }
}