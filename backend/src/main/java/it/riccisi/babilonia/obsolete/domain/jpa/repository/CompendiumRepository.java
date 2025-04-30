package it.riccisi.babilonia.obsolete.domain.jpa.repository;

import it.riccisi.babilonia.domain.jpa.entity.ProjectEntity;
import it.riccisi.babilonia.domain.jpa.entity.CompendiumEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompendiumRepository extends JpaRepository<CompendiumEntity, String> {

    List<CompendiumEntity> findByProject(ProjectEntity project);
}