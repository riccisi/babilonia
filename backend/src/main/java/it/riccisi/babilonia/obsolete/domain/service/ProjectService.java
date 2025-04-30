package it.riccisi.babilonia.obsolete.domain.service;

import it.riccisi.babilonia.domain.model.Compendium;
import it.riccisi.babilonia.domain.model.Project;
import it.riccisi.babilonia.domain.ProjectStatus;

import java.util.List;

public interface ProjectService {

    // --- Operazioni sul progetto ---
    String create(String name, String description, String system, String world, List<String> compendiums);
    Project get(String id);
    double translationProgress(String id, String language);

    // --- Stato asincrono ---
    ProjectStatus getStatus(String id);
    double creationProgress(String id);

    // --- Lettura dei progetti ---
    List<Project> findAll();
    List<Project> findByStatus(ProjectStatus status);

    // --- Operazioni sui compendi ---
    void addCompendium(String projectId, String descriptor);
    void removeCompendium(String projectId, String compendiumId);

    // --- Lettura dei compendi ---
    List<Compendium> listCompendiums(String projectId);
    Compendium getCompendium(String projectId, String compendiumId);
}