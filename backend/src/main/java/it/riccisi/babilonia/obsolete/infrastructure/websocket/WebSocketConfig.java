package it.riccisi.babilonia.obsolete.infrastructure.websocket;

import it.riccisi.babilonia.domain.websocket.WebSocketFoundrySessions;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final FoundryAuthInterceptor authInterceptor;
    private final WebSocketFoundrySessions sessions;  // decoratore & registry

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry
            .addEndpoint("/ws/foundry")
            .addInterceptors(authInterceptor)
            .setAllowedOrigins("*")
            .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry broker) {
        broker.enableSimpleBroker("/topic", "/user/queue");
        broker.setApplicationDestinationPrefixes("/app");
        broker.setUserDestinationPrefix("/user");
    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration reg) {
        reg.addDecoratorFactory(this.sessions);  // usa direttamente sessions come decorator
    }
}