package it.riccisi.babilonia.domain.jpa;

import it.riccisi.babilonia.domain.Member;
import it.riccisi.babilonia.domain.Project;
import it.riccisi.babilonia.domain.User;
import it.riccisi.babilonia.domain.jpa.entity.ProjectMembershipEntity;
import it.riccisi.babilonia.domain.jpa.entity.UserEntity;
import it.riccisi.babilonia.domain.jpa.repository.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.cactoos.iterable.Mapped;

@RequiredArgsConstructor
public class JpaUser implements User {

    @NonNull private final UserEntity user;
    @NonNull private final Repositories repo;

    @Override
    public String id() {
        return this.user.getId();
    }

    @Override
    public String username() {
        return this.user.getDisplayName();
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
}