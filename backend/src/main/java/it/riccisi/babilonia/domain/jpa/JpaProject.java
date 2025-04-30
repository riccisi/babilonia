package it.riccisi.babilonia.domain.jpa;

import it.riccisi.babilonia.domain.*;
import it.riccisi.babilonia.domain.jpa.entity.CompendiumEntity;
import it.riccisi.babilonia.domain.jpa.entity.ProjectEntity;
import it.riccisi.babilonia.domain.jpa.entity.ProjectMappingsEntity;
import it.riccisi.babilonia.domain.jpa.entity.ProjectMembershipEntity;
import it.riccisi.babilonia.domain.jpa.repository.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.cactoos.iterable.Mapped;
import org.cactoos.map.MapOf;

import java.util.Map;

/**
 * Represents a JPA-based implementation of the {@link Project} interface.
 * This class provides functionality to manage and interact with projects
 * stored in a relational database through JPA entities and repositories.
 * It is designed to enforce immutability and leverage the repository layer
 * for persistence and retrieval operations.
 * <p>
 * Responsibilities include:
 * - Retrieving project details such as ID, name, description, system, and world.
 * - Managing project mappings associated with specific {@link ItemType}.
 * - Managing project members and their roles.
 * - Handling compendiums related to the project.
 * - Tracking and updating project status and progress.
 * - Saving and deleting project data.
 * <p>
 * This class is final and cannot be extended. Dependency injection via
 * the constructor ensures that required components such as the project
 * entity and repositories are provided at runtime.
 */
@RequiredArgsConstructor
public final class JpaProject implements Project {

    @NonNull private final ProjectEntity entity;
    @NonNull private final Repositories repo;

    @Override
    public String id() {
        return this.entity.getId();
    }

    @Override
    public String name() {
        return this.entity.getName();
    }

    @Override
    public String description() {
        return this.entity.getDescription();
    }

    @Override
    public String instanceId() {
        return this.entity.getInstanceId();
    }

    @Override
    public String system() {
        return this.entity.getSystem();
    }

    @Override
    public String world() {
        return this.entity.getWorld();
    }

    @Override
    public Map<ItemType, Mappings> mappings() {
        return new MapOf<>(
            type -> type,
            type -> new JpaMappings(this.repo.projectMappings().getByItemType(type).orElse(new ProjectMappingsEntity())),
            this.repo.projectMappings().findDistinctItemTypesByProjectId(this.id())
        );
    }

    @Override
    public ProjectStatus status() {
        return this.entity.getStatus() == null ? ProjectStatus.CREATING : this.entity.getStatus();
    }

    @Override
    public double progress() {
        return this.entity.getProgress();
    }

    @Override
    public Iterable<Member> members() {
        return new Mapped<>(
            entity -> new JpaMember(entity, this.repo),
            this.repo.projectMemberships().findByProject(this.entity)
        );
    }

    @Override
    public Iterable<Compendium> compendiums() {
        return new Mapped<>(
            entity -> new JpaCompendium(entity, this.repo),
            this.repo.compendiums().findByProject(this.entity)
        );
    }

    @Override
    public void setStatus(ProjectStatus projectStatus) {
        this.entity.setStatus(projectStatus);
    }

    @Override
    public void updateProgress(double progress) {
        this.entity.setProgress(progress);
    }

    @Override
    public void addMember(User user, ProjectRole projectRole) {
        this.repo.projectMemberships().save(new ProjectMembershipEntity(
            this.repo.users().getReferenceById(user.id()),
            this.entity,
            projectRole
        ));
    }

    @Override
    public void removeMember(Member member) {
        this.repo.projectMemberships().deleteById(member.id());
    }

    @Override
    public void addCompendium(CompendiumData data) {
        this.repo.compendiums().save(
            new CompendiumEntity(
                data.key(),
                data.name(),
                data.module(),
                data.lang(),
                ItemType.valueOf(data.type()),
                data.json(),
                this.entity
            )
        );
    }

    @Override
    public void removeCompendium(Compendium compendium) {
        this.repo.compendiums().deleteById(compendium.id());
    }

    @Override
    public void save() {
        this.repo.projects().save(this.entity);
    }

    @Override
    public void delete() {
        this.repo.projects().delete(this.entity);
    }
}