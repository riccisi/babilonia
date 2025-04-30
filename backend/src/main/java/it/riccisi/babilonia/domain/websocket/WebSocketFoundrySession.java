package it.riccisi.babilonia.domain.websocket;

import it.riccisi.babilonia.domain.CompendiumData;
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
    public Future<CompendiumData> fetchCompendiumData(String key) {
        String dest = "/topic/requests/" + instanceId;
        return stompClient.request(
            dest,
            CompendiumData.class,
            Map.of("compendiumKey", key)
        );
    }
}
