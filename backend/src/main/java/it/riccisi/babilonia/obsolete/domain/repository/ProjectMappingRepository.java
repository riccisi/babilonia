package it.riccisi.babilonia.obsolete.domain.repository;

import it.riccisi.babilonia.domain.ItemType;
import it.riccisi.babilonia.domain.service.jpa.entity.ProjectMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectMappingRepository extends JpaRepository<ProjectMapping, String> {
    Optional<ProjectMapping> findByProjectIdAndItemType(String projectId, ItemType itemType);
    List<ProjectMapping> findByProjectId(String projectId);
    void deleteByProjectIdAndItemType(String projectId, ItemType itemType);
}
