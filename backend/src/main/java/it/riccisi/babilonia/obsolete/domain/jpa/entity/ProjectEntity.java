package it.riccisi.babilonia.obsolete.domain.jpa.entity;

import it.riccisi.babilonia.domain.ProjectStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.time.Instant;
import java.util.*;

@Entity
@Table(name = "project")
@NoArgsConstructor
@Getter @Setter
public final class ProjectEntity {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;
    private String description;

    @Enumerated(EnumType.STRING)
    private ProjectStatus status;

    private String instanceId;
    private String system;
    private String world;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "project_target_languages", joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "language")
    private Set<String> targetLanguages = new HashSet<>();

    private double progress;

    private Instant createdAt;
    private Instant lastSync;

    public ProjectEntity(@NonNull final String name,
                         @NonNull final String description,
                         @NonNull final String instanceId,
                         @NonNull final String system,
                         @NonNull final String world,
                         @NonNull final ProjectStatus status) {
        this.name = name;
        this.description = description;
        this.instanceId = instanceId;
        this.system = system;
        this.world = world;
        this.status = status;
        this.createdAt = Instant.now();
    }
}