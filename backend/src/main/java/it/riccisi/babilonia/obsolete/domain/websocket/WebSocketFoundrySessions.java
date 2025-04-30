package it.riccisi.babilonia.obsolete.domain.websocket;

import it.riccisi.babilonia.domain.*;
import it.riccisi.babilonia.domain.websocket.WebSocketFoundrySession;
import it.riccisi.babilonia.infrastructure.websocket.WebSocketClient;
import lombok.RequiredArgsConstructor;
import org.cactoos.iterator.Mapped;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;
import org.springframework.web.socket.handler.WebSocketHandlerDecoratorFactory;

import java.util.Iterator;
import java.util.Map;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Handles WebSocket sessions for managing Foundry VTT instances. This class implements
 * the {@link FoundrySessions} interface to provide lookup and iteration capabilities
 * for active sessions. Additionally, it implements {@link WebSocketHandlerDecoratorFactory}
 * to integrate with WebSocket handlers in order to monitor and manage session lifecycle events.
 * <p>
 * When a WebSocket connection is opened, a {@link it.riccisi.babilonia.domain.websocket.WebSocketFoundrySession} is created and added
 * to the sessions. When the connection is closed, the corresponding session is removed.
 */
@Component
@Primary
@RequiredArgsConstructor
public class WebSocketFoundrySessions implements FoundrySessions, WebSocketHandlerDecoratorFactory {

    private final WebSocketClient client;
    private final Map<String, FoundrySession> sessions = new ConcurrentHashMap<>();

    // --- FoundrySessions: solo lookup public ---
    @Override
    public Optional<FoundrySession> getByInstanceId(String connectionId) {
        return Optional.ofNullable(sessions.get(connectionId));
    }

    @Override
    public Iterator<FoundrySession> iterator() {
        return new Mapped<>(
            session -> session,
            this.sessions.values().iterator()
        );
    }

    // --- DecoratorFactory: hook open/close WebSocket ---
    @Override
    public WebSocketHandler decorate(final WebSocketHandler handler) {
        return new WebSocketHandlerDecorator(handler) {
            @Override
            public void afterConnectionEstablished(WebSocketSession ws) throws Exception {
                String connectionId = (String) ws.getAttributes().get("connectionId");
                if (connectionId == null) {
                    throw new IllegalStateException("WebSocket session connectionId not found");
                }
                FoundrySession fs = new WebSocketFoundrySession(connectionId, client);
                sessions.put(connectionId, fs);
                super.afterConnectionEstablished(ws);
            }

            @Override
            public void afterConnectionClosed(WebSocketSession ws, CloseStatus status) throws Exception {
                String connectionId = (String) ws.getAttributes().get("connectionId");
                if (connectionId != null) {
                    FoundrySession session = sessions.remove(connectionId);
                    if (session != null) {
                        session.close();
                    }
                }
                super.afterConnectionClosed(ws, status);
            }
        };
    }
}