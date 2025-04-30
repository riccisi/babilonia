package it.riccisi.babilonia.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.NonNull;

import java.util.Map;

public interface Compendium {

    @NonNull String id();
    @NonNull String key();
    @NonNull String name();
    @NonNull String module();
    @NonNull String language();
    @NonNull ItemType type();
    @NonNull Project project();
    @NonNull Mappings mappings();

    @NonNull Iterable<CompendiumItem> items(String targetLang, Map<ItemType, Mappings> mappings) throws JsonProcessingException;

    void save();
    void delete();
}