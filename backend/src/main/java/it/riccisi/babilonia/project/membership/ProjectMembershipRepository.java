package it.riccisi.babilonia.project.membership;

import it.riccisi.babilonia.project.Project;
import it.riccisi.babilonia.user.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectMembershipRepository extends JpaRepository<ProjectMembership, UUID> {
    Optional<ProjectMembership> findByUserAndProject(UserData user, Project project);
    List<ProjectMembership> findByUser(UserData user);
    List<ProjectMembership> findByProject(Project project);
}