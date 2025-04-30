package it.riccisi.babilonia.domain;

public enum ProjectRole {
    OWNER(4),
    COLLABORATOR(3),
    TRANSLATOR(2),
    READONLY(1);

    private final int level;

    ProjectRole(int level) {
        this.level = level;
    }

    public int level() {
        return level;
    }
}