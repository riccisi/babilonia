package it.riccisi.babilonia.domain;

import java.util.Optional;

public interface FoundrySessions extends Iterable<FoundrySession> {

    /** Torna la sessione attiva, se presente. */
    Optional<FoundrySession> getByInstanceId(String instanceId);
}