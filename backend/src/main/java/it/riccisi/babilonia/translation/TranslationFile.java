package it.riccisi.babilonia.translation;

import it.riccisi.babilonia.project.Project;
import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
public class TranslationFile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Setter
    @ManyToOne(optional = false)
    private Project project;

    private String language;          // es: "it", "fr"
    private String compendium;        // es: "dnd5e.items"

    @Lob
    private String content;           // JSON originale completo come stringa

    @Setter
    private Instant createdAt;
    @Setter
    private Instant updatedAt;

    public void updateFrom(TranslationFile other) {
        this.language = other.language;
        this.compendium = other.compendium;
        this.content = other.content;
        this.updatedAt = Instant.now();
    }

}