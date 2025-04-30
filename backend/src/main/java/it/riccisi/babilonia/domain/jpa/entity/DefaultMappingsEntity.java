package it.riccisi.babilonia.domain.jpa.entity;

import it.riccisi.babilonia.domain.ItemType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "default_mappings")
@NoArgsConstructor
@Getter @Setter
public class DefaultMappingsEntity {

    @Id
    @Enumerated(EnumType.STRING)
    @Getter
    private ItemType itemType;

    @ElementCollection
    @CollectionTable(name = "default_mapping_entries", joinColumns = @JoinColumn(name = "mapping_item_type"))
    @MapKeyColumn(name = "translated_field")
    @Column(name = "original_path")
    @Getter
    private Map<String, String> fields = new HashMap<>();

    public DefaultMappingsEntity(ItemType itemType, Map<String, String> fields) {
        this.itemType = itemType;
        this.fields = fields;
    }
}