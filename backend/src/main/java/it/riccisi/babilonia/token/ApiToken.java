package it.riccisi.babilonia.token;

import it.riccisi.babilonia.user.UserData;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
public final class ApiToken {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(unique = true, nullable = false)
    private String token;
    @ManyToOne(optional = false)
    private UserData userData;
    private String label;
    private String foundryInstanceId; // stabile per installazione Foundry
    private Instant createdAt;
    private boolean revoked;

    public ApiToken(String token, UserData userData, String label) {
        this.token = token;
        this.userData = userData;
        this.label = label;
        this.createdAt = Instant.now();
    }

    public boolean belongsTo(UserData userData) {
        return this.userData.equals(userData);
    }

    public void revoke() {
        this.revoked = true;
    }

    public void bindWithFoundry(String instanceId) {
        this.foundryInstanceId = instanceId;
    }
}
