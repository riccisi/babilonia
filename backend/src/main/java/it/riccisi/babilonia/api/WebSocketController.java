package it.riccisi.babilonia.api;

import it.riccisi.babilonia.infrastructure.websocket.WebSocketEventBus;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import java.util.Map;

/**
 * A controller responsible for handling WebSocket communications. This controller
 * processes incoming WebSocket messages defined by dynamic channels and actions,
 * then publishes the corresponding events to an internal event bus.
 */
@Controller
@RequiredArgsConstructor
public final class WebSocketController {

    @NonNull private final WebSocketEventBus eventBus;

    /**
     * Handles WebSocket messages with a specified channel and action. Processes
     * the received payload and publishes an event to the internal eventBus.
     *
     * @param channel the channel part of the WebSocket message destination
     * @param action the action part of the WebSocket message destination
     * @param corrId the unique correlation identifier from the message header
     * @param payload the content of the incoming WebSocket message
     */
    @MessageMapping("/{channel}/{action}")
    public void onAnyResponse(
        @DestinationVariable String channel,
        @DestinationVariable String action,
        @Header("correlation-id") String corrId,
        @Payload String payload
    ) {
        String key = channel + "_" + action;
        this.eventBus.publish(corrId, Map.entry(key, payload));
    }
}