package it.riccisi.babilonia.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketSessionRegistry {

    private final Map<String, WebSocketSession> activeSessions = new ConcurrentHashMap<>();

    public void register(String instanceId, WebSocketSession session) {
        activeSessions.put(instanceId, session);
    }

    public void unregister(String instanceId) {
        activeSessions.remove(instanceId);
    }

    public Optional<WebSocketSession> getSession(String instanceId) {
        return Optional.ofNullable(activeSessions.get(instanceId));
    }

    public int count() {
        return activeSessions.size();
    }

    public Map<String, WebSocketSession> getAllSessions() {
        return Map.copyOf(activeSessions);
    }
}
