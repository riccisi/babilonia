package it.riccisi.babilonia.domain.plain;

import it.riccisi.babilonia.domain.Mapping;
import it.riccisi.babilonia.domain.Mappings;
import lombok.RequiredArgsConstructor;
import org.cactoos.iterable.Joined;
import org.cactoos.iterable.Mapped;
import org.cactoos.set.SetOf;

import java.util.Iterator;
import java.util.Optional;

@RequiredArgsConstructor
public final class MergedMappings implements Mappings {

    private final Mappings source;
    private final Mappings target;

    @Override
    public Iterator<Mapping> iterator() {
        return new Mapped<>(
            name -> target.get(name).orElseGet(source.get(name)::get),
            new SetOf<>(
                new Joined<String>(
                    new Mapped<>(Mapping::name,source),
                    new Mapped<>(Mapping::name,target)
                )
            )
        ).iterator();
    }

    @Override
    public Optional<Mapping> get(String name) {
        return Optional.of(target.get(name).orElseGet(source.get(name)::get));
    }
}