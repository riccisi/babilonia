package it.riccisi.babilonia.domain.jpa;

import com.fasterxml.jackson.databind.JsonNode;
import it.riccisi.babilonia.domain.CompendiumItem;
import it.riccisi.babilonia.domain.ItemType;
import it.riccisi.babilonia.domain.Mappings;
import it.riccisi.babilonia.domain.TranslationStatus;
import it.riccisi.babilonia.domain.jpa.entity.TranslationEntity;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public final class JpaCompendiumItem implements CompendiumItem {

    @NonNull private final JsonNode node;
    @NonNull private final ItemType type;
    @NonNull private final List<TranslationEntity> translations;
    @NonNull private final Map<ItemType, Mappings> mappings;

    @Override
    public String id() {
        return this.node.get("_id").asText();
    }

    @Override
    public String name() {
        return this.node.get("name").asText();
    }

    @Override
    public ItemType type() {
        return this.type;
    }

    @Override
    public double progress() {
        return 0;
    }

    @Override
    public TranslationStatus status() {
        return null;
    }
}
