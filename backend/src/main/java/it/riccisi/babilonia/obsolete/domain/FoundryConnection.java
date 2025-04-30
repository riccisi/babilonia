package it.riccisi.babilonia.obsolete.domain;

import it.riccisi.babilonia.domain.ConnectionStatus;
import it.riccisi.babilonia.domain.exception.AlreadyPairedException;
import it.riccisi.babilonia.domain.exception.InvalidPairingException;

import java.util.Optional;

public interface FoundryConnection {

    String id();               // UUID generato dal SaaS
    String name();             // etichetta utente
    String secret();           // one-time secret per il pairing
    Optional<String> instanceId(); // valorizzato solo dopo il pairing
    it.riccisi.babilonia.domain.ConnectionStatus status(); // UNPAIRED, PENDING, ACTIVE, INACTIVE

    /**
     * Completa il pairing per questa connessione:
     * - imposta instanceId
     * - cambia lo stato da UNPAIRED o PENDING → ACTIVE
     * @throws InvalidPairingException se il secret non corrisponde
     * @throws AlreadyPairedException se è già paired con un’altra instanceId
     */
    void confirmPairing(String secret, String instanceId) throws InvalidPairingException, AlreadyPairedException;

    /**
     * Aggiorna lo stato (e.g. ACTIVE ↔ INACTIVE) di questa connessione.
     */
    void updateStatus(ConnectionStatus newStatus);

    void save();

    void delete();

    boolean checkToken(String token);
}
