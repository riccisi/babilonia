package it.riccisi.babilonia.obsolete.domain;

import it.riccisi.babilonia.domain.FoundrySession;

import java.util.Optional;

public interface FoundrySessions extends Iterable<it.riccisi.babilonia.domain.FoundrySession> {

    /** Torna la sessione attiva, se presente. */
    Optional<FoundrySession> get(String connectionId);
}