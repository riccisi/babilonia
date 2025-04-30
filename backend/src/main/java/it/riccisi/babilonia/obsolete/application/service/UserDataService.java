package it.riccisi.babilonia.obsolete.application.service;

import it.riccisi.babilonia.domain.jpa.entity.UserEntity;
import it.riccisi.babilonia.domain.jpa.repository.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDataService {

    @NonNull private final UserRepository userDataRepository;

    public UserEntity syncFromJwt(final Jwt jwt) {
        String externalId = jwt.getSubject();
        return userDataRepository.findByExternalId(externalId).orElseGet(() -> userDataRepository.save(new UserEntity(
            externalId,
            jwt.getClaimAsString("email"),
            jwt.getClaimAsString("name")
        )));
    }

    public Optional<UserEntity> findByEmail(@NonNull String email) {
        return this.userDataRepository.findByEmail(email);
    }
}