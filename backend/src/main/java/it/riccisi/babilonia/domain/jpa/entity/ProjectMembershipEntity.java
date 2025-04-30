package it.riccisi.babilonia.domain.jpa.entity;

import it.riccisi.babilonia.domain.ProjectRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "project_memberships")
@Getter
@NoArgsConstructor
public final class ProjectMembershipEntity {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(optional = false)
    private UserEntity user;

    @ManyToOne(optional = false)
    private ProjectEntity project;

    @Enumerated(EnumType.STRING)
    private ProjectRole role;

    public ProjectMembershipEntity(UserEntity user, ProjectEntity project, ProjectRole role) {
        this.user = user;
        this.project = project;
        this.role = role;
    }
}