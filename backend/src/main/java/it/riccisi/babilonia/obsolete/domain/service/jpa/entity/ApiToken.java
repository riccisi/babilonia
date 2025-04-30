package it.riccisi.babilonia.obsolete.domain.service.jpa.entity;

import it.riccisi.babilonia.domain.jpa.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@NoArgsConstructor
@Getter @Setter
public final class ApiToken {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true, nullable = false)
    private String token;
    @ManyToOne(optional = false)
    private UserEntity userData;
    private String label;
    private String foundryInstanceId; // stabile per installazione Foundry
    private Instant createdAt;
    private boolean revoked;

    public ApiToken(String token, UserEntity userData, String label) {
        this.token = token;
        this.userData = userData;
        this.label = label;
        this.createdAt = Instant.now();
    }

    public boolean belongsTo(UserEntity userData) {
        return this.userData.equals(userData);
    }

    public void revoke() {
        this.revoked = true;
    }

    public void bindWithFoundry(String instanceId) {
        this.foundryInstanceId = instanceId;
    }
}
