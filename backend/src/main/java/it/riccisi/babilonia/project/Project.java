package it.riccisi.babilonia.project;

import it.riccisi.babilonia.project.membership.ProjectMembership;
import it.riccisi.babilonia.project.membership.ProjectRole;
import it.riccisi.babilonia.user.UserData;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@NoArgsConstructor
public final class Project {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String worldName;
    private String systemId;
    @ElementCollection
    private List<String> compendiums;
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProjectMembership> memberships = new HashSet<>();
    @ElementCollection
    private Set<String> targetLanguages;
    @Setter
    private Instant createdAt;
    private Instant lastSync;

    public void updateFrom(Project other) {
        this.worldName = other.worldName;
        this.systemId = other.systemId;
        this.compendiums = other.compendiums;
        this.targetLanguages = other.targetLanguages;
    }

    public boolean isOwner(UserData user) {
        return this.hasRole(user, ProjectRole.OWNER);
    }

    public boolean isCollaborator(UserData user) {
        return this.hasRole(user, ProjectRole.COLLABORATOR);
    }

    public boolean isReadonly(UserData user) {
        return this.hasRole(user, ProjectRole.READONLY);
    }

    public boolean hasAccess(UserData user) {
        return memberships.stream().anyMatch(m -> m.getUser().equals(user));
    }

    public boolean canEdit(UserData user) {
        return isOwner(user) || isCollaborator(user);
    }

    public boolean canManageMembers(UserData user) {
        return isOwner(user);
    }

    private boolean hasRole(UserData user, ProjectRole role) {
        return this.memberships.stream()
            .anyMatch(m -> m.getUser().equals(user) && m.getRole() == role);
    }
}
