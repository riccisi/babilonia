package it.riccisi.babilonia.token;

import it.riccisi.babilonia.user.User;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApiTokenService {

    @NonNull private final ApiTokenRepository apiTokenRepository;

    public ApiToken generateToken(User user, String label) {
        return this.apiTokenRepository.save(new ApiToken(
            UUID.randomUUID().toString(),
            user,
            label
        ));
    }

    public List<ApiToken> findByUser(User user) {
        return this.apiTokenRepository.findByUserAndRevokedFalse(user);
    }

    public void revokeToken(Long tokenId, User user) {
        final ApiToken token = this.apiTokenRepository.findById(tokenId)
            .filter(t -> t.belongsTo(user))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        token.revoke();
        this.apiTokenRepository.save(token);
    }

    public Optional<User> authenticate(String token) {
        return apiTokenRepository.findByTokenAndRevokedFalse(token).map(ApiToken::getUser);
    }
}
