package it.riccisi.babilonia.api;

import it.riccisi.babilonia.domain.FoundryConnection;
import it.riccisi.babilonia.domain.FoundryConnections;
import it.riccisi.babilonia.domain.exception.AlreadyPairedException;
import it.riccisi.babilonia.domain.exception.InvalidPairingException;
import it.riccisi.babilonia.domain.jpa.JpaFoundryConnection;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/connections")
@RequiredArgsConstructor
public class FoundryConnectionController {

    private final FoundryConnections connections;

    @PostMapping("/pair")
    public ResponseEntity<Void> pair(@RequestBody PairRequest req) {
        FoundryConnection conn = this.connections.getBySecret(req.secret())
            .orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404), "Connessione non trovata"));
        try {
            conn.confirmPairing(req.secret(), req.instanceId());
            conn.save();
            return ResponseEntity.ok().build();
        } catch (InvalidPairingException e) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Secret non valido");
        } catch (AlreadyPairedException e) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(409), "Già associato");
        }
    }

    public record PairRequest(String secret, String instanceId) {}
}
