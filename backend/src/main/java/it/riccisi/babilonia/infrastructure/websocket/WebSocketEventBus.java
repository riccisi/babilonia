package it.riccisi.babilonia.infrastructure.websocket;

import it.riccisi.babilonia.domain.Subscription;

import java.util.function.Consumer;

public interface WebSocketEventBus {

    /**
     * Registra un handler per un evento identificato da correlationId.
     * Restituisce un Subscription che consente di rimuovere il handler.
     */
    <T> Subscription subscribe(String correlationId, Consumer<T> handler);

    /**
     * Pubblica un evento e rimuove TUTTI i handler registrati per correlationId.
     */
    <T> void publish(String correlationId, T event);
}