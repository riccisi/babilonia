package it.riccisi.babilonia.obsolete.application.service.impl;

import it.riccisi.babilonia.application.service.DefaultMappingService;
import it.riccisi.babilonia.application.service.impl.JpaDefaultMappingService;
import it.riccisi.babilonia.domain.service.jpa.entity.DefaultMapping;
import it.riccisi.babilonia.domain.ItemType;
import lombok.NonNull;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Primary
public class CachedDefaultMappingService implements DefaultMappingService {

    private final DefaultMappingService delegate;

    private Map<ItemType, DefaultMapping> cached;

    public CachedDefaultMappingService(JpaDefaultMappingService delegate) {
        this.delegate = delegate;
    }

    @Override
    public List<DefaultMapping> getAll() {
        this.ensureCacheLoaded();
        return new ArrayList<>(this.cached.values());
    }

    @Override
    public Optional<DefaultMapping> getByItemType(ItemType itemType) {
        this.ensureCacheLoaded();
        return Optional.ofNullable(this.cached.get(itemType));
    }

    @Override
    public Map<ItemType, DefaultMapping> getAllByType() {
        this.ensureCacheLoaded();
        return this.cached;
    }

    @Override
    public Map<String, String> getFieldsFor(@NonNull final ItemType itemType) {
        this.ensureCacheLoaded();
        return Optional.ofNullable(this.cached.get(itemType))
            .map(DefaultMapping::getFields)
            .orElse(Map.of());
    }

    @Override
    public void syncFromBabele(final Map<ItemType, Map<String, String>> newMappings) {
        this.delegate.syncFromBabele(newMappings);
        this.cached = null;
    }

    private void ensureCacheLoaded() {
        if (this.cached == null) {
            this.cached = this.delegate.getAllByType();
        }
    }
}
