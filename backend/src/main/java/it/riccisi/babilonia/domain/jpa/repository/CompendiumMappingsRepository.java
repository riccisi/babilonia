package it.riccisi.babilonia.domain.jpa.repository;

import it.riccisi.babilonia.domain.jpa.entity.CompendiumEntity;
import it.riccisi.babilonia.domain.jpa.entity.CompendiumMappingsEntity;
import it.riccisi.babilonia.domain.jpa.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompendiumMappingsRepository extends JpaRepository<CompendiumMappingsEntity, String> {
    Optional<CompendiumMappingsEntity> getByCompendium(CompendiumEntity compendium);
}