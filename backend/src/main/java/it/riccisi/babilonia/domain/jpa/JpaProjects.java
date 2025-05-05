package it.riccisi.babilonia.domain.jpa;

import it.riccisi.babilonia.domain.Project;
import it.riccisi.babilonia.domain.ProjectStatus;
import it.riccisi.babilonia.domain.Projects;
import it.riccisi.babilonia.domain.exception.NoProjectFoundException;
import it.riccisi.babilonia.domain.jpa.entity.ProjectEntity;
import it.riccisi.babilonia.domain.jpa.repository.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.cactoos.iterator.Mapped;
import org.springframework.stereotype.Service;

import java.util.Iterator;

@Service("projects")
@RequiredArgsConstructor
public final class JpaProjects implements Projects {

    @NonNull private final ProjectRepository projectRepo;
    @NonNull private final Repositories repo;

    @Override
    public Project create(String name, String description, String instanceId, String system, String world) {
        final ProjectEntity entity = this.projectRepo.save(new ProjectEntity(
            name,
            description,
            instanceId,
            world,
            system,
            ProjectStatus.CREATING
        ));
        return mapToJpaProject(entity);
    }

    @Override
    public Project getById(String id) throws NoProjectFoundException {
        return this.mapToJpaProject(
            this.projectRepo
            .findById(id)
            .orElseThrow(() -> new NoProjectFoundException(id))
        );
    }

    @Override
    public @NonNull Iterator<Project> iterator() {
        return new Mapped<>(
            this::mapToJpaProject,
            this.projectRepo.findAll().iterator()
        );
    }

    private JpaProject mapToJpaProject(ProjectEntity entity) {
        return new JpaProject(
            entity,
            this.repo
        );
    }
}