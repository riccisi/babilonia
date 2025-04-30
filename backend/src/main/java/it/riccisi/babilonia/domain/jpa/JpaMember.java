package it.riccisi.babilonia.domain.jpa;

import it.riccisi.babilonia.domain.Member;
import it.riccisi.babilonia.domain.Project;
import it.riccisi.babilonia.domain.User;
import it.riccisi.babilonia.domain.jpa.entity.ProjectMembershipEntity;
import it.riccisi.babilonia.domain.jpa.repository.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class JpaMember implements Member {

    @NonNull private final ProjectMembershipEntity entity;
    @NonNull private final Repositories repo;

    @Override
    public String id() {
        return this.entity.getId();
    }

    @Override
    public User user() {
        return new JpaUser(
            this.entity.getUser(),
            this.repo
        );
    }

    @Override
    public Project project() {
        return new JpaProject(
            this.entity.getProject(),
            this.repo
        );
    }

    @Override
    public String role() {
        return this.entity.getRole().name();
    }

    @Override
    public boolean canRead() {
        return this.entity.getRole().level() >= 1;
    }

    @Override
    public boolean canTranslate() {
        return this.entity.getRole().level() >= 2;
    }

    @Override
    public boolean canManageCompendium() {
        return this.entity.getRole().level() >= 3;
    }

    @Override
    public boolean canManageProject() {
        return this.entity.getRole().level() == 4;
    }
}