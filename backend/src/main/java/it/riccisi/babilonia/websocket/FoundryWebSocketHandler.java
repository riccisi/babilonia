package it.riccisi.babilonia.websocket;

import it.riccisi.babilonia.token.ApiToken;
import it.riccisi.babilonia.token.ApiTokenRepository;
import it.riccisi.babilonia.token.ApiTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FoundryWebSocketHandler extends TextWebSocketHandler {

    private final WebSocketSessionRegistry sessionRegistry;
    private final ApiTokenService apiTokenService;
    private final ApiTokenRepository apiTokenRepository;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        URI uri = session.getUri();
        String token = UriComponentsBuilder.fromUri(uri).build().getQueryParams().getFirst("token");
        String instanceId = UriComponentsBuilder.fromUri(uri).build().getQueryParams().getFirst("instanceId");

        apiTokenService.authenticate(token).ifPresentOrElse(user -> {
            Optional<ApiToken> apiToken = apiTokenRepository.findByTokenAndRevokedFalse(token);
            apiToken.ifPresent(tk -> {
                if (tk.getFoundryInstanceId() == null) {
                    tk.bindWithFoundry(instanceId);
                    apiTokenRepository.save(tk);
                } else if (!tk.getFoundryInstanceId().equals(instanceId)) {
                    try {
                        session.close(CloseStatus.NOT_ACCEPTABLE.withReason("Token associato a un'altra istanza."));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }
                sessionRegistry.register(instanceId, session);
            });
        }, () -> {
            try {
                session.close(CloseStatus.NOT_ACCEPTABLE.withReason("Token non valido."));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        URI uri = session.getUri();
        String instanceId = UriComponentsBuilder.fromUri(uri)
            .build().getQueryParams().getFirst("instanceId");
        sessionRegistry.unregister(instanceId);
    }
}
