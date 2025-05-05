package it.riccisi.babilonia.domain;

import it.riccisi.babilonia.domain.exception.UserNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Optional;

public interface Users extends Iterable<User> {

    User getCurrent(Jwt jwt);
    User getById(String id) throws UserNotFoundException;
}