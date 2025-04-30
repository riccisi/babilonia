package it.riccisi.babilonia.domain.inmemory;

import it.riccisi.babilonia.infrastructure.websocket.InMemoryWebSocketEventBus;
import it.riccisi.babilonia.infrastructure.websocket.WebSocketEventBus;
import it.riccisi.babilonia.domain.Subscription;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.concurrent.Executors.*;
import static org.junit.jupiter.api.Assertions.*;

class InMemoryWebSocketEventBusTest {

    private WebSocketEventBus bus;

    @BeforeEach
    void setUp() {
        bus = new InMemoryWebSocketEventBus();
    }

    @Test
    void testSingleSubscriberReceivesEvent() {
        String corrId = "abc";
        AtomicReference<String> received = new AtomicReference<>(null);

        Subscription sub = bus.subscribe(corrId, (String payload) -> {
            received.set(payload);
        });

        bus.publish(corrId, "hello");

        assertEquals("hello", received.get(), "Subscriber should receive the published payload");
        // dopo il publish, gli handler sono rimossi
        AtomicReference<String> second = new AtomicReference<>("no");
        bus.subscribe(corrId, second::set);
        bus.publish(corrId, "world");
        assertEquals("no", second.get(), "No handler should be invoked after previous publish");
    }

    @Test
    void testUnsubscribeBeforePublish() {
        String corrId = "x1";
        AtomicReference<String> received = new AtomicReference<>(null);

        Subscription sub = bus.subscribe(corrId, received::set);
        sub.unsubscribe(); // rimuovo subito
        bus.publish(corrId, "data");

        assertNull(received.get(), "Subscriber unsubscribed should not receive events");
    }

    @Test
    void testMultipleSubscribers() {
        String corrId = "multi";
        AtomicBoolean one = new AtomicBoolean(false);
        AtomicBoolean two = new AtomicBoolean(false);

        bus.subscribe(corrId, payload -> one.set(true));
        bus.subscribe(corrId, payload -> two.set(true));

        bus.publish(corrId, "ok");

        assertTrue(one.get(), "First subscriber should be called");
        assertTrue(two.get(), "Second subscriber should be called");
    }

    @Test
    void testPublishWithoutSubscribersIsNoOp() {
        // nessun subscribe → publish non deve fallire
        assertDoesNotThrow(() -> bus.publish("no-subs", "nothing"));
    }

    @Test
    void testThreadSafetyUnderConcurrentSubscribeAndPublish() throws InterruptedException {
        String corrId = "concurrent";
        int threads = 20;
        ExecutorService exec = newFixedThreadPool(threads);
        CyclicBarrier barrier = new CyclicBarrier(threads);
        CountDownLatch latch = new CountDownLatch(threads);
        AtomicInteger count = new AtomicInteger(0);

        // ogni thread fa subscribe, barrier, publish
        for (int i = 0; i < threads; i++) {
            exec.submit(() -> {
                bus.subscribe(corrId, payload -> count.incrementAndGet());
                try { barrier.await(); } catch (Exception ignored) {}
                bus.publish(corrId, "data");
                latch.countDown();
            });
        }
        latch.await();
        // ciascun publish (20) rimuove tutti i subscriber alla prima invocazione,
        // quindi al massimo il primo publish conta N volte e poi rimuove i handler.
        int received = count.get();
        assertTrue(received >= threads, "At least each subscriber of the first publish should fire");
        exec.shutdown();
    }
}