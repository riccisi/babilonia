package it.riccisi.babilonia.domain.jpa;

import it.riccisi.babilonia.domain.FoundryConnection;
import it.riccisi.babilonia.domain.Member;
import it.riccisi.babilonia.domain.Project;
import it.riccisi.babilonia.domain.User;
import it.riccisi.babilonia.domain.exception.NoUserConnectionFound;
import it.riccisi.babilonia.domain.exception.TooManyConnectionsException;
import it.riccisi.babilonia.domain.jpa.entity.FoundryConnectionEntity;
import it.riccisi.babilonia.domain.jpa.entity.ProjectMembershipEntity;
import it.riccisi.babilonia.domain.jpa.entity.UserEntity;
import it.riccisi.babilonia.domain.jpa.repository.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.cactoos.iterable.Mapped;

@RequiredArgsConstructor
public class JpaUser implements User {

    @NonNull private final UserEntity entity;
    @NonNull private final Repositories repo;

    @Override
    public String id() {
        return this.entity.getId();
    }

    @Override
    public String username() {
        return this.entity.getDisplayName();
    }

    @Override
    public Member asMemberOf(Project project) {
        final ProjectMembershipEntity membership = this.repo.projectMemberships().findByProjectIdAndUserId(project.id(), this.id()).orElseThrow(
            () -> new IllegalArgumentException("User not member of project")
        );
        return new JpaMember(
            membership,
            this.repo
        );
    }

    @Override
    public Iterable<Project> projects() {
        return new Mapped<>(
            membership -> new JpaProject(
                membership.getProject(),
                this.repo
            ),
            this.repo.projectMemberships().findByUserId(this.id())
        );
    }

    @Override
    public Iterable<FoundryConnection> connections() {
        return new Mapped<>(
            entity -> new JpaFoundryConnection(entity, repo),
            this.repo.connections().findByOwner(this.entity)
        );
    }

    @Override
    public FoundryConnection connectionByInstanceId(String name) {
        return new JpaFoundryConnection(
            this.repo.connections()
                .findByOwnerAndInstanceId(this.entity, name)
                .orElseThrow(() -> new NoUserConnectionFound(this.entity.getDisplayName(), name)),
            this.repo
        );
    }

    @Override
    public FoundryConnection connectionBySecret(String secret) {
        return new JpaFoundryConnection(
            this.repo.connections()
                .findByOwnerAndSecret(this.entity, secret)
                .orElseThrow(() -> new NoUserConnectionFound(this.entity.getDisplayName(), secret)),
            this.repo
        );
    }

    @Override
    public FoundryConnection addConnection(String name) {
        return new JpaFoundryConnection(
            this.repo.connections().save(
                new FoundryConnectionEntity(name, this.entity)
            ),
            this.repo
        );
    }
}