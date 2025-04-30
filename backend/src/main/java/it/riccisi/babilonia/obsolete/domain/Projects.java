package it.riccisi.babilonia.obsolete.domain;

import it.riccisi.babilonia.domain.Project;

import java.util.Optional;

public interface Projects extends Iterable<it.riccisi.babilonia.domain.Project> {

    it.riccisi.babilonia.domain.Project create(String name, String description, String instanceId, String system, String world);

    Optional<Project> getById(String id);
}