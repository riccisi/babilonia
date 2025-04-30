package it.riccisi.babilonia.domain.jpa;

import it.riccisi.babilonia.domain.Mapping;
import it.riccisi.babilonia.domain.Mappings;
import it.riccisi.babilonia.domain.jpa.entity.CompendiumMappingsEntity;
import it.riccisi.babilonia.domain.jpa.entity.DefaultMappingsEntity;
import it.riccisi.babilonia.domain.jpa.entity.ProjectMappingsEntity;
import it.riccisi.babilonia.domain.plain.MapMappings;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Iterator;

import java.util.Optional;

@RequiredArgsConstructor
public final class JpaMappings implements Mappings {

    @NonNull private final Mappings mappings;

    public JpaMappings(CompendiumMappingsEntity entity) {
        this(new MapMappings(entity.getFields()));
    }

    public JpaMappings(ProjectMappingsEntity entity) {
        this(new MapMappings(entity.getFields()));
    }

    public JpaMappings(DefaultMappingsEntity entity) {
        this(new MapMappings(entity.getFields()));
    }

    @Override
    public Optional<Mapping> get(String name) {
        return this.mappings.get(name);
    }

    @Override
    public @NonNull Iterator<Mapping> iterator() {
        return this.mappings.iterator();
    }
}