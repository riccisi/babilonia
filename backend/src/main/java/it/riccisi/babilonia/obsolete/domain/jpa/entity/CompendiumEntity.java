package it.riccisi.babilonia.obsolete.domain.jpa.entity;

import it.riccisi.babilonia.domain.ItemType;
import it.riccisi.babilonia.domain.jpa.entity.ProjectEntity;
import it.riccisi.babilonia.domain.service.jpa.entity.CompendiumMapping;
import it.riccisi.babilonia.domain.service.jpa.entity.JpaTranslationEntry;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
public class CompendiumEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String key;
    private String name;

    private String originalModule; // modulo Foundry originale associato, se presente
    private String originalLanguage;

    @Enumerated(EnumType.STRING)
    private ItemType itemType;

    @Column(columnDefinition = "jsonb")
    private String originalJson; // Salvataggio JSON originale del compendio (aggiornato periodicamente)

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private it.riccisi.babilonia.domain.jpa.entity.ProjectEntity project;

    @OneToOne(mappedBy = "compendium", cascade = CascadeType.ALL, orphanRemoval = true)
    private CompendiumMapping mapping;

    @OneToMany(mappedBy = "compendium", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JpaTranslationEntry> entries = new ArrayList<>();

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CompendiumEntity(String key, String name, String originalModule, String originalLanguage, ItemType type, String json, ProjectEntity project) {
        this.key = name;
        this.name = name;
        this.originalModule = originalModule;
        this.originalLanguage = originalLanguage;
        this.itemType = type;
        this.originalJson = json;
        this.project = project;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}