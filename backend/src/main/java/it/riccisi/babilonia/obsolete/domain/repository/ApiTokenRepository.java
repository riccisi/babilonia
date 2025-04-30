package it.riccisi.babilonia.obsolete.domain.repository;

import it.riccisi.babilonia.domain.service.jpa.entity.ApiToken;
import it.riccisi.babilonia.domain.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApiTokenRepository extends JpaRepository<ApiToken, Long> {

    Optional<ApiToken> findByTokenAndRevokedFalse(String token);

    List<ApiToken> findByUserDataAndRevokedFalse(UserEntity userData);
}