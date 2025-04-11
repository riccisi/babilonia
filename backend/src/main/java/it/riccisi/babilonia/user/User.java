package it.riccisi.babilonia.user;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@NoArgsConstructor
public class User {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(unique = true, nullable = false)
    private String externalId;
    @Column(nullable = false)
    private String email;
    private String displayName;
    private String preferredLanguage;
    private boolean notificationsEnabled;
    private Instant createdAt;

    public User(String externalId, String email, String displayName) {
        this.externalId = externalId;
        this.email = email;
        this.displayName = displayName;
        this.createdAt = Instant.now();
    }
}