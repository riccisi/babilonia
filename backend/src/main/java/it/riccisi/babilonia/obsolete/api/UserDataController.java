package it.riccisi.babilonia.obsolete.api;

import it.riccisi.babilonia.domain.jpa.entity.UserEntity;
import it.riccisi.babilonia.application.service.UserDataService;
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
    public ResponseEntity<UserEntity> getUser(@AuthenticationPrincipal Jwt jwt) {
        final UserEntity userData = this.userDataService.syncFromJwt(jwt);
        return ResponseEntity.ok(userData);
    }
}