package it.riccisi.babilonia.domain.jpa.repository;

import it.riccisi.babilonia.domain.jpa.entity.FoundryConnectionEntity;
import it.riccisi.babilonia.domain.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FoundryConnectionRepository extends JpaRepository<FoundryConnectionEntity, String> {

    List<FoundryConnectionEntity> findByOwner(UserEntity owner);
    Optional<FoundryConnectionEntity> findByOwnerAndInstanceId(UserEntity owner, String instanceId);
    Optional<FoundryConnectionEntity> findByOwnerAndSecret(UserEntity owner, String secret);
}