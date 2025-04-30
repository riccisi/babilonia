package it.riccisi.babilonia.domain.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "user_data")
@NoArgsConstructor
@Getter @Setter
public class UserEntity {

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

    public UserEntity(String externalId, String email, String displayName) {
        this.externalId = externalId;
        this.email = email;
        this.displayName = displayName;
        this.createdAt = Instant.now();
    }
}