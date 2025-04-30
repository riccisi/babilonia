package it.riccisi.babilonia.domain.jpa.entity;

import it.riccisi.babilonia.domain.ItemType;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "compendiums")
@NoArgsConstructor
@Getter @Setter
public class CompendiumEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String key;
    private String name;

    private String module;
    private String language;

    @Enumerated(EnumType.STRING)
    private ItemType itemType;

    @Column(columnDefinition = "jsonb")
    private String json;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private ProjectEntity project;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CompendiumEntity(String key, String name, String module, String language, ItemType type, String json, ProjectEntity project) {
        this.key = key;
        this.name = name;
        this.module = module;
        this.language = language;
        this.itemType = type;
        this.json = json;
        this.project = project;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}