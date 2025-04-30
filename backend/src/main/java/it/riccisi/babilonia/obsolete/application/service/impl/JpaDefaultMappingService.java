package it.riccisi.babilonia.obsolete.application.service.impl;

import it.riccisi.babilonia.application.service.DefaultMappingService;
import it.riccisi.babilonia.domain.service.jpa.entity.DefaultMapping;
import it.riccisi.babilonia.domain.ItemType;
import it.riccisi.babilonia.domain.repository.DefaultMappingRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JpaDefaultMappingService implements DefaultMappingService {

    private final DefaultMappingRepository repository;

    @Override
    public List<DefaultMapping> getAll() {
        return this.repository.findAll();
    }

    @Override
    public Optional<DefaultMapping> getByItemType(final ItemType itemType) {
        return this.repository.findById(itemType);
    }

    @Override
    public Map<ItemType, DefaultMapping> getAllByType() {
        return this.repository.findAll().stream()
            .collect(Collectors.toUnmodifiableMap(DefaultMapping::getItemType, Function.identity()));
    }

    @Override
    public Map<String, String> getFieldsFor(@NonNull final ItemType itemType) {
        return this.repository.findById(itemType)
            .map(DefaultMapping::getFields)
            .orElse(Map.of());
    }

    // Solo uso admin o interno
    @Override
    public void syncFromBabele(final Map<ItemType, Map<String, String>> newMappings) {
        final List<DefaultMapping> mappings = newMappings.entrySet().stream()
            .map(entry -> new DefaultMapping(entry.getKey(), entry.getValue(), LocalDateTime.now()))
            .toList();

        this.repository.saveAll(mappings);
    }
}
