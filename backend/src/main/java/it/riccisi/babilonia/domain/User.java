package it.riccisi.babilonia.domain;

import it.riccisi.babilonia.domain.exception.TooManyConnectionsException;

public interface User {

    String id();
    String username();
    Member asMemberOf(Project project);
    Iterable<Project> projects();
    Iterable<FoundryConnection> connections();
    FoundryConnection connectionByInstanceId(String name);
    FoundryConnection connectionBySecret(String secret);
    FoundryConnection addConnection(String name);
}