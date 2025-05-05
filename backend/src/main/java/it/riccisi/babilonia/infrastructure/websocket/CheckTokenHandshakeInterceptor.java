package it.riccisi.babilonia.infrastructure.websocket;

import it.riccisi.babilonia.domain.FoundryConnection;
import it.riccisi.babilonia.domain.Users;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@Component
@RequiredArgsConstructor
public final class CheckTokenHandshakeInterceptor implements HandshakeInterceptor {

    @NonNull private final Users users;

    @Override
    public boolean beforeHandshake(
        ServerHttpRequest request,
        @NonNull ServerHttpResponse response,
        @NonNull WebSocketHandler wsHandler,
        @NonNull Map<String, Object> attributes) {

        final URI uri = request.getURI();
        final MultiValueMap<String, String> params = UriComponentsBuilder.fromUri(uri).build().getQueryParams();
        final String userId      = params.getFirst("userId");
        final String token      = params.getFirst("token");
        final String instanceId = params.getFirst("instanceId");

        final FoundryConnection conn = this.users.getById(userId).connectionByInstanceId(instanceId);
        if(!conn.checkToken(token)) {
            response.setStatusCode(HttpStatus.FORBIDDEN);
            return false;
        }

        attributes.put("connection", conn);
        return true;
    }

    @Override
    public void afterHandshake(
        ServerHttpRequest request,
        ServerHttpResponse response,
        WebSocketHandler wsHandler,
        Exception exception) {
        // do nothing
    }
}
