package it.riccisi.babilonia.domain.jpa.repository;

import it.riccisi.babilonia.domain.jpa.entity.CompendiumEntity;
import it.riccisi.babilonia.domain.jpa.entity.ProjectEntity;
import it.riccisi.babilonia.domain.jpa.entity.TranslationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TranslationRepository extends JpaRepository<TranslationEntity, String> {
    List<TranslationEntity> findByCompendiumAndLanguage(CompendiumEntity compendium, String language);
}