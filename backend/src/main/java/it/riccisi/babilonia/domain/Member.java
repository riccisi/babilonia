package it.riccisi.babilonia.domain;

public interface Member {

    String id();

    User user();

    Project project();

    String role();

    boolean canRead();

    boolean canTranslate();

    boolean canManageCompendium();

    boolean canManageProject();
}