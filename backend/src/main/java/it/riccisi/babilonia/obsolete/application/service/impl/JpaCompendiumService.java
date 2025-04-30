package it.riccisi.babilonia.obsolete.application.service.impl;

import it.riccisi.babilonia.application.service.CompendiumService;
import it.riccisi.babilonia.domain.jpa.entity.CompendiumEntity;
import it.riccisi.babilonia.domain.jpa.repository.CompendiumRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public final class JpaCompendiumService implements CompendiumService {

    private final CompendiumRepository repository;

    public double computeProgress(@NonNull String compendiumId, @NonNull String language) {
        CompendiumEntity compendium = this.repository.findById(compendiumId)
            .orElseThrow(() -> new EntityNotFoundException("Compendio non trovato"));
        return compendium.progress(language);
    }
}