package it.riccisi.babilonia.domain.jpa.repository;

import it.riccisi.babilonia.domain.ProjectStatus;
import it.riccisi.babilonia.domain.jpa.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, String> {

    List<ProjectEntity> findByStatus(ProjectStatus status);
}