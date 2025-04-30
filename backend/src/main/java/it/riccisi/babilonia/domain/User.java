package it.riccisi.babilonia.domain;

public interface User {

    String id();
    String username();

    Member asMemberOf(Project project);

    Iterable<Project> projects();
}