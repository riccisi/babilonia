package it.riccisi.babilonia.obsolete.domain.service.jpa.entity;

import it.riccisi.babilonia.domain.jpa.entity.CompendiumEntity;
import it.riccisi.babilonia.domain.model.Mapping;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Entity
@NoArgsConstructor
@Getter @Setter
public class CompendiumMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToOne
    @JoinColumn(name = "compendium_id", nullable = false, unique = true)
    private CompendiumEntity compendium;

    @ElementCollection
    @CollectionTable(name = "compendium_mapping_entries", joinColumns = @JoinColumn(name = "mapping_id"))
    @MapKeyColumn(name = "translated_field")
    @Column(name = "original_path")
    @Getter
    private Map<String, String> fields = new HashMap<>();

    private LocalDateTime lastUpdated;

    public Mapping toModel() {
        return new Mapping(this.fields);
    }
}
