package it.riccisi.babilonia.domain;

import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Optional;

public interface Users extends Iterable<User> {

    /**
     *
     * @return
     */
    User getCurrent(Jwt jwt);

    /**
     * Recupera un utente per id, se esiste.
     */
    Optional<User> get(String id);
}