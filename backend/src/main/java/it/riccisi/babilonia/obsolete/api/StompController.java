package it.riccisi.babilonia.obsolete.api;

import it.riccisi.babilonia.domain.WebSocketEventBus;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public final class StompController {

    @NonNull private final WebSocketEventBus WSEventBus;

    /**
     * Cattura TUTTO su /app/compendium/json, /app/progress/update, ecc.
     */
    @MessageMapping("/{channel}/{action}")
    public void onAnyResponse(
        @DestinationVariable String channel,
        @DestinationVariable String action,
        @Header("correlation-id") String corrId,
        @Payload String payload
    ) {
        // ricostruiamo un key logico, es "compendiumJson" o "progressUpdate"
        String key = channel + "_" + action;
        this.WSEventBus.publish(corrId, Map.entry(key, payload));
    }
}
