package it.riccisi.babilonia.obsolete.domain;

import it.riccisi.babilonia.domain.FoundryConnection;

import java.util.Optional;

public interface FoundryConnections extends Iterable<it.riccisi.babilonia.domain.FoundryConnection> {

    it.riccisi.babilonia.domain.FoundryConnection create(String name);
    Optional<it.riccisi.babilonia.domain.FoundryConnection> getById(String id);
    Optional<it.riccisi.babilonia.domain.FoundryConnection> getBySecret(String secret);
    Optional<FoundryConnection> getByInstanceId(String instanceId);
}