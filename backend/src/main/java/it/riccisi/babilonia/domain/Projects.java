package it.riccisi.babilonia.domain;

import java.util.Optional;

public interface Projects extends Iterable<Project> {

    Project create(String name, String description, String instanceId, String system, String world);

    Optional<Project> getById(String id);
}