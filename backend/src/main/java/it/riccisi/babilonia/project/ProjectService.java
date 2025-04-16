package it.riccisi.babilonia.project;

import it.riccisi.babilonia.project.membership.ProjectMembership;
import it.riccisi.babilonia.project.membership.ProjectMembershipRepository;
import it.riccisi.babilonia.project.membership.ProjectRole;
import it.riccisi.babilonia.user.UserData;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMembershipRepository membershipRepository;

    public Optional<Project> findById(UUID id) {
        return this.projectRepository.findById(id);
    }

    public Project save(Project project) {
        return this.projectRepository.save(project);
    }

    public void delete(Project project) {
        this.projectRepository.delete(project);
    }

    public List<Project> getProjectsForUser(UserData user) {
        return this.membershipRepository.findByUser(user).stream()
            .map(ProjectMembership::getProject)
            .distinct()
            .toList();
    }

    public void addMember(Project project, UserData user, ProjectRole role) {
        this.membershipRepository.save(new ProjectMembership(user, project, role));
    }

    public void removeMember(Project project, UserData user) {
        this.membershipRepository.findByUserAndProject(user, project).ifPresent(membershipRepository::delete);
    }
}
