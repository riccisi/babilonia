package it.riccisi.babilonia.domain.jpa;

import it.riccisi.babilonia.domain.User;
import it.riccisi.babilonia.domain.Users;
import it.riccisi.babilonia.domain.jpa.entity.UserEntity;
import it.riccisi.babilonia.domain.jpa.repository.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.cactoos.iterator.Mapped;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Optional;

/**
 * Implementation of the {@link Users} interface using JPA repositories.
 * Provides methods to manage and retrieve user entities within the system.
 */
@Service("users")
@RequiredArgsConstructor
public final class JpaUsers implements Users {

    @NonNull private final Repositories repo;

    @Override
    public User getCurrent(Jwt jwt) {
        final String externalId = jwt.getSubject();
        final UserEntity entity = this.repo.users().findByExternalId(externalId).orElseGet(
            () -> this.repo.users().save(
                new UserEntity(
                    externalId,
                    jwt.getClaimAsString("email"),
                    jwt.getClaimAsString("name")
                )
            )
        );
        return this.mapToJpaUser(entity);
    }

    @Override
    public @NonNull Iterator<User> iterator() {
        return new Mapped<>(
            this::mapToJpaUser,
            this.repo.users().findAll().iterator()
        );
    }

    @Override
    public User getById(String id) {
        return this.mapToJpaUser(
            this.repo.users().findById(id).orElseThrow()
        );
    }

    private JpaUser mapToJpaUser(UserEntity entity) {
        return new JpaUser(
            entity,
            this.repo
        );
    }
}