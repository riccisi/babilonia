package it.riccisi.babilonia.obsolete.domain.jpa;

import it.riccisi.babilonia.domain.Member;
import it.riccisi.babilonia.domain.Project;
import it.riccisi.babilonia.domain.User;
import it.riccisi.babilonia.domain.jpa.JpaMember;
import it.riccisi.babilonia.domain.jpa.JpaProject;
import it.riccisi.babilonia.domain.jpa.entity.ProjectMembershipEntity;
import it.riccisi.babilonia.domain.jpa.entity.UserEntity;
import it.riccisi.babilonia.domain.jpa.repository.CompendiumRepository;
import it.riccisi.babilonia.domain.jpa.repository.ProjectMembershipRepository;
import it.riccisi.babilonia.domain.jpa.repository.ProjectRepository;
import it.riccisi.babilonia.domain.jpa.repository.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.cactoos.iterable.Mapped;

@RequiredArgsConstructor
public class JpaUser implements User {

    @NonNull private final UserEntity user;
    @NonNull private final ProjectRepository projectRepo;
    @NonNull private final ProjectMembershipRepository membershipRepo;
    @NonNull private final CompendiumRepository compendiumRepo;
    @NonNull private final UserRepository userRepo;

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
        final ProjectMembershipEntity membership = membershipRepo.findByProjectIdAndUserId(project.id(), this.id()).orElseThrow(
            () -> new IllegalArgumentException("User not member of project")
        );
        return new JpaMember(
            membership,
            this.membershipRepo,
            this.projectRepo,
            this.compendiumRepo,
            this.userRepo
        );
    }

    @Override
    public Iterable<Project> projects() {
        return new Mapped<>(
            membership -> new JpaProject(
                membership.getProject(),
                this.projectRepo,
                this.compendiumRepo,
                this.membershipRepo,
                this.userRepo
            ),
            this.membershipRepo.findByUserId(this.id())
        );
    }
}