package it.riccisi.babilonia.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDataRepository extends JpaRepository<UserData, Long> {
    Optional<UserData> findByExternalId(String externalId);
    Optional<UserData> findByEmail(String email);
}
