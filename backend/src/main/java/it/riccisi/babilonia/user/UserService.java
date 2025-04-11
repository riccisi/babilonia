package it.riccisi.babilonia.user;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class UserService {

    @NonNull private final UserRepository userRepository;

    public User syncFromJwt(final Jwt jwt) {
        String externalId = jwt.getSubject();
        return userRepository.findByExternalId(externalId).orElseGet(() -> userRepository.save(new User(
            externalId,
            jwt.getClaimAsString("email"),
            jwt.getClaimAsString("name")
        )));
    }
}