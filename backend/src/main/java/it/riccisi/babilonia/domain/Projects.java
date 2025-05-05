package it.riccisi.babilonia.domain;

import it.riccisi.babilonia.domain.exception.NoProjectFoundException;

public interface Projects extends Iterable<Project> {

    Project create(String name, String description, String instanceId, String system, String world);

    Project getById(String id) throws NoProjectFoundException;
}