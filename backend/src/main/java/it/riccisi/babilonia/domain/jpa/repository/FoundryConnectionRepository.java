package it.riccisi.babilonia.domain.jpa.repository;

import it.riccisi.babilonia.domain.jpa.entity.FoundryConnectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FoundryConnectionRepository extends JpaRepository<FoundryConnectionEntity, String> {

    Optional<FoundryConnectionEntity> findBySecret(String secret);
    Optional<FoundryConnectionEntity> findByInstanceId(String instanceId);
}