package it.riccisi.babilonia.translation;

import jakarta.persistence.Embeddable;

@Embeddable
public class TranslationEntry {
    private String key;               // es: "Item.abc123.name"
    private String original;          // valore originale
    private String translated;        // valore tradotto
    private String originalHash;      // hash del valore originale per confronti future
}
