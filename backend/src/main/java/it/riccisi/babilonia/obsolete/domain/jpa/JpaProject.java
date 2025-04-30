package it.riccisi.babilonia.obsolete.domain.jpa;

import it.riccisi.babilonia.domain.*;
import it.riccisi.babilonia.domain.jpa.JpaCompendium;
import it.riccisi.babilonia.domain.jpa.JpaMember;
import it.riccisi.babilonia.domain.jpa.entity.CompendiumEntity;
import it.riccisi.babilonia.domain.jpa.entity.ProjectEntity;
import it.riccisi.babilonia.domain.jpa.entity.ProjectMembershipEntity;
import it.riccisi.babilonia.domain.jpa.repository.ProjectMembershipRepository;
import it.riccisi.babilonia.domain.jpa.repository.ProjectRepository;
import it.riccisi.babilonia.domain.jpa.repository.CompendiumRepository;
import it.riccisi.babilonia.domain.jpa.repository.UserRepository;
import it.riccisi.babilonia.domain.ItemType;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.cactoos.iterable.Mapped;

@RequiredArgsConstructor
public final class JpaProject implements Project {

    @NonNull private final ProjectEntity entity;
    @NonNull private final ProjectRepository projectRepo;
    @NonNull private final CompendiumRepository compendiumRepo;
    @NonNull private final ProjectMembershipRepository membershipRepo;
    @NonNull private final UserRepository userRepository;

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
            entity -> new JpaMember(
                entity,
                this.membershipRepo,
                this.projectRepo,
                this.compendiumRepo,
                this.userRepository
            ),
            this.membershipRepo.findByProject(this.entity)
        );
    }

    @Override
    public Iterable<Compendium> compendiums() {
        return new Mapped<>(
            compendium -> new JpaCompendium(compendium, this.compendiumRepo),
            this.compendiumRepo.findByProject(this.entity)
        );
    }

    @Override
    public void addMember(User user, ProjectRole role)  {
        this.membershipRepo.save(new ProjectMembershipEntity(
            this.userRepository.getReferenceById(user.id()),
            this.entity,
            role
        ));
    }

    @Override
    public void addCompendium(String key, String name, String module, String lang, ItemType type, String json) {
        this.compendiumRepo.save(new CompendiumEntity(
            key,
            name,
            module,
            lang,
            type,
            json,
            this.entity
        ));
    }

    @Override
    public void save() {
        this.projectRepo.save(this.entity);
    }

    @Override
    public void setStatus(ProjectStatus projectStatus) {
        this.entity.setStatus(projectStatus);
    }

    @Override
    public void updateProgress(double progress) {
        this.entity.setProgress(progress);
    }
}