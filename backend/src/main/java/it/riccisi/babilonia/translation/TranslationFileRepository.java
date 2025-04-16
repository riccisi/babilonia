package it.riccisi.babilonia.translation;

import it.riccisi.babilonia.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TranslationFileRepository extends JpaRepository<TranslationFile, UUID> {
    List<TranslationFile> findByProjectAndLanguage(Project project, String language);
    Optional<TranslationFile> findByProjectAndLanguageAndCompendium(Project project, String language, String compendium);
}