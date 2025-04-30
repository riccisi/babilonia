package it.riccisi.babilonia.domain.jpa;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.riccisi.babilonia.domain.*;
import it.riccisi.babilonia.domain.jpa.entity.CompendiumEntity;
import it.riccisi.babilonia.domain.jpa.entity.CompendiumMappingsEntity;
import it.riccisi.babilonia.domain.jpa.entity.TranslationEntity;
import it.riccisi.babilonia.domain.jpa.repository.*;
import it.riccisi.babilonia.domain.plain.MergedMappings;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Joined;
import org.cactoos.iterable.Mapped;
import org.cactoos.map.MapOf;
import org.cactoos.set.SetOf;


import java.util.List;
import java.util.Map;

/**
 * The JpaCompendium class serves as a concrete implementation of the {@link Compendium} interface
 * and provides functionality for managing and interacting with compendiums stored in a JPA-backed
 * persistence layer.
 * <p>
 * This class uses a {@link CompendiumEntity} as the data source and delegates repository operations
 * to the provided {@link Repositories}. Additionally, it leverages an {@link ObjectMapper} for JSON
 * processing.
 * <p>
 * Key functionalities include retrieving compendium metadata (such as ID, key, name, module, language, and type),
 * accessing related data such as projects and mappings, fetching items from the compendium with optional
 * mappings, and persisting or deleting the compendium.
 * <p>
 * Instances of this class are immutable.
 */
@RequiredArgsConstructor
public final class JpaCompendium implements Compendium {

    @NonNull private final CompendiumEntity entity;
    @NonNull private final Repositories repo;
    @NonNull private final ObjectMapper mapper;

    public JpaCompendium(@NonNull CompendiumEntity entity, @NonNull Repositories repo) {
        this(entity, repo, new ObjectMapper());
    }

    @Override
    public @NonNull String id() {
        return this.entity.getId();
    }

    @Override
    public @NonNull String key() {
        return this.entity.getKey();
    }

    @Override
    public @NonNull String name() {
        return this.entity.getName();
    }

    @Override
    public @NonNull String module() {
        return this.entity.getModule();
    }

    @Override
    public @NonNull String language() {
        return this.entity.getLanguage();
    }

    @Override
    public @NonNull ItemType type() {
        return this.entity.getItemType();
    }

    @Override
    public @NonNull Project project() {
        return new JpaProject(
            this.entity.getProject(),
            this.repo
        );
    }

    @Override
    public @NonNull Mappings mappings() {
        return new JpaMappings(
            this.repo.compendiumMappings().getByCompendium(this.entity).orElse(new CompendiumMappingsEntity())
        );
    }

    @Override
    public @NonNull Iterable<CompendiumItem> items(@NonNull String targetLanguage, @NonNull Map<ItemType, Mappings> defaultMappings) throws JsonProcessingException {

        final List<TranslationEntity> translations = this.repo.translations().findByCompendiumAndLanguage(this.entity, targetLanguage);

        final Map<ItemType, Mappings> projectMappings = this.project().mappings();
        final Map<ItemType, Mappings> mappings = new MapOf<>(
            key -> key,
            key -> new MergedMappings(
                new MergedMappings(
                    defaultMappings.getOrDefault(key, Mappings.EMPTY),
                    projectMappings.getOrDefault(key, Mappings.EMPTY)
                ),
                key == this.type() ? this.mappings() : Mappings.EMPTY
            ),
            new SetOf<>(
                new Joined<>(
                    defaultMappings.keySet(),
                    projectMappings.keySet(),
                    new IterableOf<>(this.type())
                )
            )
        );

        return new Mapped<>(
            node -> new JpaCompendiumItem(node, this.type(), translations, mappings),
            this.mapper.readTree(this.entity.getJson())
        );
    }

    @Override
    public void save() {
        this.repo.compendiums().save(this.entity);
    }

    @Override
    public void delete() {
        this.repo.compendiums().delete(this.entity);
    }
}