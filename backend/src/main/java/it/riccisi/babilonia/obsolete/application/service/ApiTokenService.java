package it.riccisi.babilonia.obsolete.application.service;

import it.riccisi.babilonia.domain.service.jpa.entity.ApiToken;
import it.riccisi.babilonia.domain.repository.ApiTokenRepository;
import it.riccisi.babilonia.domain.jpa.entity.UserEntity;
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

    public ApiToken generateToken(UserEntity userData, String label) {
        return this.apiTokenRepository.save(new ApiToken(
            UUID.randomUUID().toString(),
            userData,
            label
        ));
    }

    public List<ApiToken> findByUser(UserEntity userData) {
        return this.apiTokenRepository.findByUserDataAndRevokedFalse(userData);
    }

    public void revokeToken(Long tokenId, UserEntity userData) {
        final ApiToken token = this.apiTokenRepository.findById(tokenId)
            .filter(t -> t.belongsTo(userData))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        token.revoke();
        this.apiTokenRepository.save(token);
    }

    public Optional<UserEntity> authenticate(String token) {
        return apiTokenRepository.findByTokenAndRevokedFalse(token).map(ApiToken::getUserData);
    }
}
