package it.riccisi.babilonia.domain.jpa.repository;

import it.riccisi.babilonia.domain.ItemType;
import it.riccisi.babilonia.domain.jpa.entity.DefaultMappingsEntity;
import it.riccisi.babilonia.domain.jpa.entity.ProjectMappingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectMappingsRepository extends JpaRepository<ProjectMappingsEntity, String> {
    List<ItemType> findDistinctItemTypesByProjectId(String projectId);
    Optional<ProjectMappingsEntity> getByItemType(ItemType itemType);
}