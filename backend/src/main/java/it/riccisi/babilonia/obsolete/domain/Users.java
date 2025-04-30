package it.riccisi.babilonia.obsolete.domain;

import it.riccisi.babilonia.domain.User;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Optional;

public interface Users extends Iterable<it.riccisi.babilonia.domain.User> {

    /**
     *
     * @return
     */
    it.riccisi.babilonia.domain.User getCurrent(Jwt jwt);

    /**
     * Recupera un utente per id, se esiste.
     */
    Optional<User> get(String id);
}