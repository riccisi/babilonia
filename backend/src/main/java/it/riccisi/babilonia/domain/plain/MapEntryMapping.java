package it.riccisi.babilonia.domain.plain;

import it.riccisi.babilonia.domain.Mapping;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public final class MapEntryMapping implements Mapping {

    @NonNull private final Map.Entry<String, String> entry;

    @Override
    public String name() {
        return this.entry.getKey();
    }

    @Override
    public String path() {
        return this.entry.getValue();
    }
}
