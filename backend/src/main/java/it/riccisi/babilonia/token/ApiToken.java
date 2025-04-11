package it.riccisi.babilonia.token;

import it.riccisi.babilonia.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@RequiredArgsConstructor
@Getter
public final class ApiToken {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(unique = true, nullable = false)
    private String token;
    @ManyToOne(optional = false)
    private User user;
    private String label;
    private String foundryInstanceId; // stabile per installazione Foundry
    private Instant createdAt;
    private boolean revoked;

    public ApiToken(String token, User user, String label) {
        this.token = token;
        this.user = user;
        this.label = label;
        this.createdAt = Instant.now();
    }

    public boolean belongsTo(User user) {
        return this.user.equals(user);
    }

    public void revoke() {
        this.revoked = true;
    }

    public void bindWithFoundry(String instanceId) {
        this.foundryInstanceId = instanceId;
    }
}
