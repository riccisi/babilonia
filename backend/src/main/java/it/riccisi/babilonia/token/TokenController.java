package it.riccisi.babilonia.token;

import it.riccisi.babilonia.user.User;
import it.riccisi.babilonia.user.UserService;
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
    @NonNull private final UserService userService;

    @GetMapping
    public ResponseEntity<List<ApiToken>> list(@AuthenticationPrincipal Jwt jwt) {
        final User user = this.userService.syncFromJwt(jwt);
        return ResponseEntity.ok(this.tokenService.findByUser(user));
    }

    @PostMapping
    public ResponseEntity<ApiToken> create(@AuthenticationPrincipal Jwt jwt, @RequestParam String label) {
        final User user = this.userService.syncFromJwt(jwt);
        ApiToken token = this.tokenService.generateToken(user, label);
        return ResponseEntity.ok(token);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
        final User user = this.userService.syncFromJwt(jwt);
        this.tokenService.revokeToken(id, user);
        return ResponseEntity.noContent().build();
    }
}