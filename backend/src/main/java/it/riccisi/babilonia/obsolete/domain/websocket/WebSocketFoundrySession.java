package it.riccisi.babilonia.obsolete.domain.websocket;

import it.riccisi.babilonia.domain.FoundrySession;
import it.riccisi.babilonia.infrastructure.websocket.WebSocketClient;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.concurrent.Future;

@RequiredArgsConstructor
public final class WebSocketFoundrySession implements FoundrySession {

    private final String instanceId;
    private final WebSocketClient stompClient;

    @Override
    public String instanceId() {
        return this.instanceId;
    }

    @Override
    public Future<String> fetchCompendiumData(String key) {
        String dest = "/topic/requests/" + instanceId;
        return stompClient.request(
            dest,
            String.class,
            Map.of("compendiumKey", key)
        );
    }
}
