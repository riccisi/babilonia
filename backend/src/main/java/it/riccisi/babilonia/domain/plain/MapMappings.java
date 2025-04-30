package it.riccisi.babilonia.domain.plain;

import it.riccisi.babilonia.domain.Mapping;
import it.riccisi.babilonia.domain.Mappings;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.cactoos.iterator.Mapped;
import org.cactoos.map.MapEntry;

import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public final class MapMappings implements Mappings {

    @NonNull
    private final Map<String, String> map;

    @Override
    public Optional<Mapping> get(String name) {
        return Optional.of(this.map.get(name)).map(path -> new MapEntryMapping(new MapEntry<>(name, path)));
    }

    @Override
    public @NonNull Iterator<Mapping> iterator() {
        return new Mapped<>(
            MapEntryMapping::new,
            this.map.entrySet().iterator()
        );
    }
}