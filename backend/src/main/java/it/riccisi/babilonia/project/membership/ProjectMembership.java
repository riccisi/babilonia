package it.riccisi.babilonia.project.membership;

import it.riccisi.babilonia.project.Project;
import it.riccisi.babilonia.user.UserData;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public final class ProjectMembership {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(optional = false)
    private UserData user;

    @ManyToOne(optional = false)
    private Project project;

    @Enumerated(EnumType.STRING)
    private ProjectRole role;

    public ProjectMembership(UserData user, Project project, ProjectRole role) {
        this.user = user;
        this.project = project;
        this.role = role;
    }
}
