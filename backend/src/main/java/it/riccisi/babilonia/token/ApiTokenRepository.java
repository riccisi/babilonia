package it.riccisi.babilonia.token;

import it.riccisi.babilonia.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApiTokenRepository extends JpaRepository<ApiToken, Long> {

    Optional<ApiToken> findByTokenAndRevokedFalse(String token);

    List<ApiToken> findByUserAndRevokedFalse(User user);
}