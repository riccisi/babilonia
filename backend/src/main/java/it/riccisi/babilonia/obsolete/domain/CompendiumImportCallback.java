package it.riccisi.babilonia.obsolete.domain;

@FunctionalInterface
public interface CompendiumImportCallback {

    void callback(int index, String key, String name, String type, String json);
}