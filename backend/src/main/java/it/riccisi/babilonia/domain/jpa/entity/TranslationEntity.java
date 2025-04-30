package it.riccisi.babilonia.domain.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "translations", uniqueConstraints = @UniqueConstraint(columnNames = {
    "compendium_id", "itemId", "propertyName", "language"
}))
@NoArgsConstructor
@Getter @Setter
public class TranslationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String itemId;
    private String propertyName;
    private String language;

    @Column(columnDefinition = "TEXT")
    private String original;
    private String originalHash;

    @Column(columnDefinition = "TEXT")
    private String translation;

    private String lastPublishedHash;
    private LocalDateTime lastUpdated;

    @ManyToOne
    @JoinColumn(name = "compendium_id", nullable = false)
    private CompendiumEntity compendium;
}