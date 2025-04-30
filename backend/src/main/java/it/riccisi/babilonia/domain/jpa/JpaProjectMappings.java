package it.riccisi.babilonia.domain.jpa;

import it.riccisi.babilonia.domain.Mapping;
import it.riccisi.babilonia.domain.Mappings;
import it.riccisi.babilonia.domain.jpa.entity.CompendiumMappingsEntity;
import it.riccisi.babilonia.domain.plain.MapEntryMapping;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.cactoos.iterator.Mapped;
import org.cactoos.map.MapEntry;

import java.util.Iterator;
import java.util.Optional;

@RequiredArgsConstructor
public final class JpaProjectMappings implements Mappings {

    @NonNull
    private final CompendiumMappingsEntity entity;

    @Override
    public Optional<Mapping> get(String name) {
        return Optional.of(this.entity.getFields().get(name)).map(path -> new MapEntryMapping(new MapEntry<>(name, path)));
    }

    @Override
    public @NonNull Iterator<Mapping> iterator() {
        return new Mapped<>(
            MapEntryMapping::new,
            this.entity.getFields().entrySet().iterator()
        );
    }
}
