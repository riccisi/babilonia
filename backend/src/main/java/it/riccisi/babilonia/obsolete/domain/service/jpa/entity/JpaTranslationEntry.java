package it.riccisi.babilonia.obsolete.domain.service.jpa.entity;

import it.riccisi.babilonia.domain.jpa.entity.CompendiumEntity;
import it.riccisi.babilonia.domain.model.TranslationEntry;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {
    "compendium_id", "itemId", "jsonPath", "language"
}))
@NoArgsConstructor
public class JpaTranslationEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Getter
    private String itemId;
    @Getter
    private String itemName;
    @Getter
    private String itemType;
    @Getter
    private String jsonPath;

    @Column(columnDefinition = "TEXT")
    @Getter
    private String translated;

    @Column(columnDefinition = "TEXT")
    @Getter
    private String original;

    @Getter
    private String originalHash;
    private String lastPublishedHash;

    @Getter
    private String language;

    @ManyToOne
    @JoinColumn(name = "compendium_id", nullable = false)
    private CompendiumEntity compendium;

    private LocalDateTime lastUpdated;

    public JpaTranslationEntry(@NonNull final CompendiumEntity compendium,
                               @NonNull final String itemId,
                               @NonNull final String jsonPath,
                               @NonNull final String language,
                               @NonNull final String originalHash,
                               final String translated) {
        this.compendium = compendium;
        this.itemId = itemId;
        this.jsonPath = jsonPath;
        this.language = language;
        this.originalHash = originalHash;
        this.translated = translated;
    }

    public TranslationEntry toModel() {
        return new TranslationEntry(
            this.itemId,
            this.jsonPath,
            this.language,
            this.originalHash,
            this.translated
        );
    }
}