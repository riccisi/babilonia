package it.riccisi.babilonia.obsolete.domain.jpa;

import it.riccisi.babilonia.domain.User;
import it.riccisi.babilonia.domain.Users;
import it.riccisi.babilonia.domain.jpa.JpaUser;
import it.riccisi.babilonia.domain.jpa.entity.UserEntity;
import it.riccisi.babilonia.domain.jpa.repository.ProjectMembershipRepository;
import it.riccisi.babilonia.domain.jpa.repository.ProjectRepository;
import it.riccisi.babilonia.domain.jpa.repository.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.cactoos.iterator.Mapped;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Optional;

@Service("users")
@RequiredArgsConstructor
public final class JpaUsers implements Users {

    @NonNull final UserRepository userRepo;
    @NonNull private final ProjectRepository projectRepo;
    @NonNull private final ProjectMembershipRepository membershipRepo;

    @Override
    public User getCurrent(Jwt jwt) {
        final String externalId = jwt.getSubject();
        final UserEntity entity = this.userRepo.findByExternalId(externalId).orElseGet(
            () -> this.userRepo.save(
                new UserEntity(
                    externalId,
                    jwt.getClaimAsString("email"),
                    jwt.getClaimAsString("name")
                )
            )
        );
        return new it.riccisi.babilonia.domain.jpa.JpaUser(
            entity,
            projectRepo,
            membershipRepo
        );
    }

    @Override
    public Iterator<User> iterator() {
        return new Mapped<>(
            entity -> new it.riccisi.babilonia.domain.jpa.JpaUser(
                entity,
                projectRepo,
                membershipRepo
            ),
            this.userRepo.findAll().iterator()
        );
    }

    @Override
    public Optional<User> get(String id) {
        return this.userRepo.findById(id)
            .map(entity -> new JpaUser(entity, projectRepo, membershipRepo));
    }
}