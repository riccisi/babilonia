package it.riccisi.babilonia.user;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDataService {

    @NonNull private final UserDataRepository userDataRepository;

    public UserData syncFromJwt(final Jwt jwt) {
        String externalId = jwt.getSubject();
        return userDataRepository.findByExternalId(externalId).orElseGet(() -> userDataRepository.save(new UserData(
            externalId,
            jwt.getClaimAsString("email"),
            jwt.getClaimAsString("name")
        )));
    }

    public Optional<UserData> findByEmail(@NonNull String email) {
        return this.userDataRepository.findByEmail(email);
    }
}