package it.riccisi.babilonia.obsolete.domain.jpa;

import it.riccisi.babilonia.domain.Member;
import it.riccisi.babilonia.domain.Project;
import it.riccisi.babilonia.domain.User;
import it.riccisi.babilonia.domain.jpa.JpaProject;
import it.riccisi.babilonia.domain.jpa.JpaUser;
import it.riccisi.babilonia.domain.jpa.entity.ProjectMembershipEntity;
import it.riccisi.babilonia.domain.jpa.repository.CompendiumRepository;
import it.riccisi.babilonia.domain.jpa.repository.ProjectMembershipRepository;
import it.riccisi.babilonia.domain.jpa.repository.ProjectRepository;
import it.riccisi.babilonia.domain.jpa.repository.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class JpaMember implements Member {

    @NonNull private final ProjectMembershipEntity entity;
    @NonNull private final ProjectMembershipRepository membershipRepo;
    @NonNull private final ProjectRepository projectRepo;
    @NonNull private final CompendiumRepository compendiumRepo;
    @NonNull private final UserRepository userRepo;

    @Override
    public User user() {
        return new JpaUser(
            this.entity.getUser(),
            this.projectRepo,
            this.membershipRepo,
            this.compendiumRepo,
            this.userRepo
        );
    }

    @Override
    public Project project() {
        return new JpaProject(
            this.entity.getProject(),
            this.projectRepo,
            this.compendiumRepo,
            this.membershipRepo,
            this.userRepo
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