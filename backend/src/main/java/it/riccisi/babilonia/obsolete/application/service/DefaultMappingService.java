package it.riccisi.babilonia.obsolete.application.service;

import it.riccisi.babilonia.domain.service.jpa.entity.DefaultMapping;
import it.riccisi.babilonia.domain.ItemType;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface DefaultMappingService {

    List<DefaultMapping> getAll();
    Optional<DefaultMapping> getByItemType(final ItemType itemType);
    Map<ItemType, DefaultMapping> getAllByType();
    Map<String, String> getFieldsFor(ItemType itemType);
    void syncFromBabele(Map<ItemType, Map<String, String>> newMappings);
}