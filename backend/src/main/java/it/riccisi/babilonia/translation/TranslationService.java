package it.riccisi.babilonia.translation;

import it.riccisi.babilonia.project.Project;
import it.riccisi.babilonia.project.ProjectRepository;
import it.riccisi.babilonia.project.ProjectService;
import it.riccisi.babilonia.user.UserDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TranslationService {
    private final TranslationFileRepository translationRepository;
    private final ProjectRepository projectRepository;
    private final ProjectService projectService;
    private final UserDataService userService;

    public TranslationFile save(TranslationFile file) {
        //file.updateFrom(Instant.now());
        return translationRepository.save(file);
    }

    public List<TranslationFile> list(Project project, String language) {
        return translationRepository.findByProjectAndLanguage(project, language);
    }

    public Optional<TranslationFile> get(UUID id) {
        return translationRepository.findById(id);
    }
}