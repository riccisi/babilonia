package it.riccisi.babilonia.obsolete.domain;

import it.riccisi.babilonia.domain.Subscription;

import java.util.function.Consumer;

public interface EventBus {

    /**
     * Registra un handler per un evento identificato da eventId.
     * Restituisce un Subscription che consente di rimuovere il handler.
     */
    <T> Subscription subscribe(String eventId, Consumer<T> handler);

    /**
     * Pubblica un evento e rimuove TUTTI i handler registrati per eventId.
     */
    <T> void publish(String eventId, T event);
}