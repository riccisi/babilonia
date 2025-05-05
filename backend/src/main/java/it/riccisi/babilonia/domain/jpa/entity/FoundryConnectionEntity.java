package it.riccisi.babilonia.domain.jpa.entity;

import it.riccisi.babilonia.domain.ConnectionStatus;
import it.riccisi.babilonia.domain.jpa.JpaUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name="foundry_connections")
@NoArgsConstructor
@Getter @Setter
public final class FoundryConnectionEntity {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;
    private String secret;
    private String instanceId;   // nullable

    @Enumerated(EnumType.STRING)
    private ConnectionStatus status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity owner;

    public FoundryConnectionEntity(String name, UserEntity owner) {
        this.name = name;
        this.secret = UUID.randomUUID().toString();
        this.status = ConnectionStatus.UNPAIRED;
        this.owner = owner;
    }
}