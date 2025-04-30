package it.riccisi.babilonia.infrastructure.websocket;

import it.riccisi.babilonia.domain.Subscription;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
public class InMemoryWebSocketEventBus implements WebSocketEventBus {

    private final ConcurrentMap<String, CopyOnWriteArrayList<Consumer<Object>>> handlers = new ConcurrentHashMap<>();

    @Override
    public <T> Subscription subscribe(String correlationId, Consumer<T> handler) {

        final CopyOnWriteArrayList<Consumer<Object>> list =
            this.handlers.computeIfAbsent(correlationId, id -> new CopyOnWriteArrayList<>());

        // wrapper serve a poter rimuovere esattamente questa lambda
        Consumer<Object> wrapper = evt -> handler.accept((T) evt);
        list.add(wrapper);

        return () -> {
            CopyOnWriteArrayList<Consumer<Object>> current = this.handlers.get(correlationId);
            if (current != null) {
                current.remove(wrapper);
                if (current.isEmpty()) {
                    this.handlers.remove(correlationId, current);
                }
            }
        };
    }

    @Override
    public <T> void publish(String correlationId, T event) {
        final CopyOnWriteArrayList<Consumer<Object>> list = this.handlers.remove(correlationId);
        if (list != null) {
            list.forEach(h -> h.accept(event));
        }
    }
}