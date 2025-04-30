package it.riccisi.babilonia.domain.jpa.repository;

import it.riccisi.babilonia.domain.ItemType;
import it.riccisi.babilonia.domain.jpa.entity.CompendiumEntity;
import it.riccisi.babilonia.domain.jpa.entity.CompendiumMappingsEntity;
import it.riccisi.babilonia.domain.jpa.entity.DefaultMappingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DefaultMappingsRepository extends JpaRepository<DefaultMappingsEntity, String> {
    Optional<DefaultMappingsEntity> getByItemType(ItemType itemType);
}