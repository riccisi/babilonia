package it.riccisi.babilonia.obsolete.domain.jpa;

import it.riccisi.babilonia.domain.Project;
import it.riccisi.babilonia.domain.ProjectStatus;
import it.riccisi.babilonia.domain.Projects;
import it.riccisi.babilonia.domain.jpa.JpaProject;
import it.riccisi.babilonia.domain.jpa.entity.ProjectEntity;
import it.riccisi.babilonia.domain.jpa.repository.CompendiumRepository;
import it.riccisi.babilonia.domain.jpa.repository.ProjectMembershipRepository;
import it.riccisi.babilonia.domain.jpa.repository.ProjectRepository;
import it.riccisi.babilonia.domain.jpa.repository.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.cactoos.iterator.Mapped;
import org.springframework.stereotype.Service;

import java.util.Iterator;

@Service("projects")
@RequiredArgsConstructor
public final class JpaProjects implements Projects {

    @NonNull private final ProjectRepository repository;
    @NonNull private final CompendiumRepository compendiumRepository;
    @NonNull private final ProjectMembershipRepository membershipRepository;
    @NonNull private final UserRepository userRepository;

    @Override
    public Project create(String name, String description, String instanceId, String system, String world) {
        final ProjectEntity entity = this.repository.save(new ProjectEntity(
            name,
            description,
            instanceId,
            world,
            system,
            ProjectStatus.CREATING
        ));
        return new it.riccisi.babilonia.domain.jpa.JpaProject(
            entity,
            this.repository,
            this.compendiumRepository,
            this.membershipRepository,
            this.userRepository
        );
    }

    @Override
    public Iterator<Project> iterator() {
        return new Mapped<>(
            JpaProject::new,
            this.repository.findAll().iterator()
        );
    }
}
