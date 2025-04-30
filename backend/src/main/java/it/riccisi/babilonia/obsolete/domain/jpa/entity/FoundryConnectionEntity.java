package it.riccisi.babilonia.obsolete.domain.jpa.entity;

import it.riccisi.babilonia.domain.ConnectionStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name="foundry_connection")
@Getter @Setter
public final class FoundryConnectionEntity {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;
    private String secret;
    private String instanceId;   // nullable

    @Enumerated(EnumType.STRING)
    private ConnectionStatus status;

    public FoundryConnectionEntity(String name) {
        this.name = name;
        this.secret = UUID.randomUUID().toString();
        this.status = ConnectionStatus.UNPAIRED;
    }
}