package it.riccisi.babilonia.obsolete.domain.jpa;

import it.riccisi.babilonia.domain.Compendium;
import it.riccisi.babilonia.domain.jpa.entity.CompendiumEntity;
import it.riccisi.babilonia.domain.jpa.repository.CompendiumRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class JpaCompendium implements Compendium {

    @NonNull private final CompendiumEntity entity;
    @NonNull private final CompendiumRepository compendiumRepo;
}