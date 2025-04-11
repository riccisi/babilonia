package it.riccisi.babilonia.project;

import it.riccisi.babilonia.user.User;
import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public final class Project {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String worldName;
    private String systemId;
    @ElementCollection
    private List<String> compendiums;
    @ManyToOne
    private User owner;
    @ManyToMany
    private Set<User> collaborators;
    @ElementCollection
    private Set<String> targetLanguages;
    private Instant createdAt;
    private Instant lastSync;
}
