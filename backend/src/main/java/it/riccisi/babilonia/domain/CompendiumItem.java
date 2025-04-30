package it.riccisi.babilonia.domain;

public interface CompendiumItem {

    String id();
    String name();
    ItemType type();
    double progress();
    TranslationStatus status();
}