package it.riccisi.babilonia.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ws")
@RequiredArgsConstructor
public class WebSocketAdminController {

    private final WebSocketSessionRegistry sessionRegistry;

    @GetMapping("/sessions")
    public ResponseEntity<Map<String, String>> listActiveSessions() {
        Map<String, String> summary = sessionRegistry.getAllSessions().entrySet().stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                e -> e.getValue().getRemoteAddress() != null ? e.getValue().getRemoteAddress().toString() : "unknown"
            ));
        return ResponseEntity.ok(summary);
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> count() {
        return ResponseEntity.ok(sessionRegistry.count());
    }
}

