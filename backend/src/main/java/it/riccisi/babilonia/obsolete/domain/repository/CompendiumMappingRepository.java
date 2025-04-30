package it.riccisi.babilonia.obsolete.domain.repository;

import it.riccisi.babilonia.domain.service.jpa.entity.CompendiumMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompendiumMappingRepository extends JpaRepository<CompendiumMapping, String> {
    Optional<CompendiumMapping> findByCompendiumId(String compendiumId);
    void deleteByCompendiumId(String compendiumId);
}