package it.riccisi.babilonia.obsolete.application.service;

import it.riccisi.babilonia.domain.ItemType;
import it.riccisi.babilonia.domain.jpa.entity.ProjectEntity;
import it.riccisi.babilonia.domain.service.jpa.entity.ProjectMapping;
import it.riccisi.babilonia.domain.repository.ProjectMappingRepository;
import it.riccisi.babilonia.domain.jpa.repository.ProjectRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProjectMappingService {

    @NonNull final ProjectMappingRepository repository;
    @NonNull private final ProjectRepository projectRepository;

    public ProjectMappingService(ProjectMappingRepository repository, ProjectRepository projectRepository) {
        this.repository = repository;
        this.projectRepository = projectRepository;
    }

    public List<ProjectMapping> getAll(final String projectId) {
        return this.repository.findByProjectId(projectId);
    }

    public Optional<ProjectMapping> get(final String projectId, final ItemType itemType) {
        return this.repository.findByProjectIdAndItemType(projectId, itemType);
    }

    public ProjectMapping update(final String projectId, ItemType itemType, Map<String, String> fields) {
        ProjectEntity project = this.projectRepository.findById(projectId)
            .orElseThrow(() -> new EntityNotFoundException("Project not found"));

        ProjectMapping mapping = this.repository.findByProjectIdAndItemType(projectId, itemType)
            .orElse(new ProjectMapping());

        mapping.update(
            project,
            itemType,
            fields
        );

        return this.repository.save(mapping);
    }

    public void delete(String projectId, ItemType itemType) {
        this.repository.deleteByProjectIdAndItemType(projectId, itemType);
    }
}

