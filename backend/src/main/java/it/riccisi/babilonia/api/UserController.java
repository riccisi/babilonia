package it.riccisi.babilonia.api;

import it.riccisi.babilonia.domain.User;
import it.riccisi.babilonia.domain.Users;
import it.riccisi.babilonia.infrastructure.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final Users users;

    @GetMapping("/me")
    public ResponseEntity<String> getUser(@CurrentUser User user) {
        return ResponseEntity.ok(user.username());
    }
}