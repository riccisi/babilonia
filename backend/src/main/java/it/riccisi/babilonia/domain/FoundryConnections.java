package it.riccisi.babilonia.domain;

import java.util.Optional;

public interface FoundryConnections extends Iterable<FoundryConnection> {

    FoundryConnection create(String name);
    Optional<FoundryConnection> getById(String id);
    Optional<FoundryConnection> getBySecret(String secret);
    Optional<FoundryConnection> getByInstanceId(String instanceId);
}