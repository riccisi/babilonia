package it.riccisi.babilonia.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserDataController {

    private final UserDataService userDataService;

    @GetMapping("/me")
    public ResponseEntity<UserData> getUser(@AuthenticationPrincipal Jwt jwt) {
        final UserData userData = this.userDataService.syncFromJwt(jwt);
        return ResponseEntity.ok(userData);
    }
}