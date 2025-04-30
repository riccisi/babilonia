package it.riccisi.babilonia.obsolete.infrastructure.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.riccisi.babilonia.domain.WebSocketEventBus;
import it.riccisi.babilonia.domain.Subscription;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;

@Component
@RequiredArgsConstructor
public final class WebSocketClient {

    private final SimpMessagingTemplate messaging;
    private final WebSocketEventBus WSEventBus;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
    private final ObjectMapper mapper = new ObjectMapper();

    /** Timeout di default per ogni richiesta */
    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(10);

    /**
     * Invia un payload qualsiasi su una destinazione STOMP e ritorna
     * un Future con la risposta di tipo R.
     *
     * @param <P>   tipo del payload di request
     * @param <R>   tipo del payload di response
     * @param destination  es. "/topic/requests/{instanceId}"
     * @param responseType        la classe di risposta (per deserializzare)
     * @param payload          il corpo della richiesta
     * @return Future con il payload di risposta
     */
    public <P,R> Future<R> request(
        String destination,
        Class<R> responseType,
        P payload
    ) {
        String corrId = UUID.randomUUID().toString();
        CompletableFuture<R> future = new CompletableFuture<>();

        // 1) subscribe temporaneo sul bus
        Subscription sub = this.WSEventBus.subscribe(corrId, (Object raw) -> {
            // deserializza in R
            R resp = mapper.convertValue(raw, responseType);
            future.complete(resp);
        });

        // 2) programma il timeout
        this.scheduler.schedule(() -> {
            if (!future.isDone()) {
                sub.unsubscribe();
                future.completeExceptionally(
                    new TimeoutException("STOMP request timeout for " + corrId)
                );
            }
        }, DEFAULT_TIMEOUT.toMillis(), TimeUnit.MILLISECONDS);

        // 3) invia la request con header di correlazione
        this.messaging.convertAndSend(
            destination,
            Map.of(
                "correlationId", corrId,
                "payload", payload
            )
        );

        // 4) pulisci subscription alla fine
        future.whenComplete((r,e) -> sub.unsubscribe());
        return future;
    }
}