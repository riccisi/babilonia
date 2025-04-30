package it.riccisi.babilonia.obsolete.domain.service.jpa.entity;

import it.riccisi.babilonia.domain.ItemType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Entity
@NoArgsConstructor
@Getter @Setter
public class DefaultMapping {

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

    public DefaultMapping(ItemType itemType, Map<String, String> fields) {
        this.itemType = itemType;
        this.fields = fields;
    }

    public Map<String, Object> toView() {
        return Map.of(
            "itemType", this.itemType,
            "fields", this.fields
        );
    }
}