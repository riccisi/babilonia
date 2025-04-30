package it.riccisi.babilonia.domain.jpa.entity;

import it.riccisi.babilonia.domain.ItemType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Entity
@Table(
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"project_id", "itemType"})
    }
)
@NoArgsConstructor
@Getter @Setter
public class ProjectMappingsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Enumerated(EnumType.STRING)
    @Getter
    private ItemType itemType;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private ProjectEntity project;

    @ElementCollection
    @CollectionTable(name = "project_mapping_entries", joinColumns = @JoinColumn(name = "mapping_id"))
    @MapKeyColumn(name = "translated_field")
    @Column(name = "json_path")
    @Getter
    private Map<String, String> fields = new HashMap<>();

}