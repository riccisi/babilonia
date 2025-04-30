package it.riccisi.babilonia.domain;

public interface CompendiumItemProperty {

    String name();
    String path();
    String original();
    String translation();
    boolean isTranslated();
    boolean isOutdated();
}