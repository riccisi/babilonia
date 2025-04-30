package it.riccisi.babilonia.domain.jpa.repository;

import it.riccisi.babilonia.domain.jpa.entity.ProjectEntity;
import it.riccisi.babilonia.domain.jpa.entity.ProjectMembershipEntity;
import it.riccisi.babilonia.domain.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectMembershipRepository extends JpaRepository<ProjectMembershipEntity, String> {

    List<ProjectMembershipEntity> findByProject(ProjectEntity project);
    Optional<ProjectMembershipEntity> findByProjectIdAndUserId(String projectId, String userId);
    List<ProjectMembershipEntity> findByUserId(String userId);
}