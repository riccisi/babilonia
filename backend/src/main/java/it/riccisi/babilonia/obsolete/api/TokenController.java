package it.riccisi.babilonia.obsolete.api;

import it.riccisi.babilonia.application.service.ApiTokenService;
import it.riccisi.babilonia.domain.service.jpa.entity.ApiToken;
import it.riccisi.babilonia.domain.jpa.entity.UserEntity;
import it.riccisi.babilonia.application.service.UserDataService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tokens")
@RequiredArgsConstructor
public class TokenController {

    @NonNull private final ApiTokenService tokenService;
    @NonNull private final UserDataService userDataService;

    @GetMapping
    public ResponseEntity<List<ApiToken>> list(@AuthenticationPrincipal Jwt jwt) {
        final UserEntity userData = this.userDataService.syncFromJwt(jwt);
        return ResponseEntity.ok(this.tokenService.findByUser(userData));
    }

    @PostMapping
    public ResponseEntity<ApiToken> create(@AuthenticationPrincipal Jwt jwt, @RequestParam String label) {
        final UserEntity userData = this.userDataService.syncFromJwt(jwt);
        ApiToken token = this.tokenService.generateToken(userData, label);
        return ResponseEntity.ok(token);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
        final UserEntity userData = this.userDataService.syncFromJwt(jwt);
        this.tokenService.revokeToken(id, userData);
        return ResponseEntity.noContent().build();
    }
}