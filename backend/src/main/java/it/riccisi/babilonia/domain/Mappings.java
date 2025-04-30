package it.riccisi.babilonia.domain;

import org.cactoos.iterator.IteratorOf;

import java.util.Iterator;
import java.util.Optional;

public interface Mappings extends Iterable<Mapping> {

    Mappings EMPTY = new EmptyMappings();

    Optional<Mapping> get(String name);

    class EmptyMappings implements Mappings {

        @Override
        public Optional<Mapping> get(String name) {
            return Optional.empty();
        }

        @Override
        public Iterator<Mapping> iterator() {
            return new IteratorOf<>();
        }
    }
}