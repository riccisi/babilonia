package it.riccisi.babilonia.obsolete.domain.jpa.repository;

import it.riccisi.babilonia.domain.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> findByExternalId(String externalId);
    Optional<UserEntity> findByEmail(String email);
}