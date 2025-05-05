package it.riccisi.babilonia.api;

import it.riccisi.babilonia.domain.FoundryConnection;
import it.riccisi.babilonia.domain.User;
import it.riccisi.babilonia.domain.exception.AlreadyPairedException;
import it.riccisi.babilonia.domain.exception.InvalidPairingException;
import it.riccisi.babilonia.infrastructure.security.CurrentUser;
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
public class ConnectionController {

    @PostMapping("/pair")
    public ResponseEntity<Void> pair(
        @CurrentUser User user,
        @RequestBody PairRequest req) {

        final FoundryConnection conn = user.connectionBySecret(req.secret());
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
