package it.riccisi.babilonia.obsolete.api;

import it.riccisi.babilonia.domain.jpa.entity.ProjectEntity;
import it.riccisi.babilonia.domain.jpa.entity.ProjectMembershipEntity;
import it.riccisi.babilonia.domain.ProjectRole;
import it.riccisi.babilonia.domain.jpa.repository.ProjectMembershipRepository;
import it.riccisi.babilonia.domain.jpa.repository.ProjectRepository;
import it.riccisi.babilonia.application.service.ProjectService;
import it.riccisi.babilonia.domain.jpa.entity.UserEntity;
import it.riccisi.babilonia.application.service.UserDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/projects/{projectId}/members")
@RequiredArgsConstructor
public class ProjectMembershipController {

    private final ProjectRepository projectRepository;
    private final ProjectMembershipRepository membershipRepository;
    private final ProjectService projectService;
    private final UserDataService userService;

    @GetMapping
    public ResponseEntity<List<ProjectMembershipEntity>> listMembers(
        @PathVariable String projectId,
        @AuthenticationPrincipal Jwt jwt) {

        final ProjectEntity project = getProject(projectId);
        final UserEntity currentUser = this.userService.syncFromJwt(jwt);
        this.checkOwnership(currentUser, project);

        return ResponseEntity.ok(this.membershipRepository.findByProject(project));
    }

    @PostMapping
    public ResponseEntity<Void> addMember(
        @AuthenticationPrincipal Jwt jwt,
        @PathVariable String projectId,
        @RequestParam String userEmail,
        @RequestParam ProjectRole role) {

        final ProjectEntity project = getProject(projectId);
        final UserEntity currentUser = this.userService.syncFromJwt(jwt);
        this.checkOwnership(currentUser, project);

        final UserEntity user = this.getUser(userEmail);
        this.projectService.addMember(project, user, role);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> removeMember(
        @AuthenticationPrincipal Jwt jwt,
        @PathVariable String projectId,
        @RequestParam String userEmail) {

        final ProjectEntity project = this.getProject(projectId);
        final UserEntity currentUser = this.userService.syncFromJwt(jwt);
        this.checkOwnership(currentUser, project);

        final UserEntity user = this.getUser(userEmail);
        this.projectService.removeMember(project, user);

        return ResponseEntity.noContent().build();
    }

    private ProjectEntity getProject(String projectId) {
        return this.projectRepository.findById(projectId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    private UserEntity getUser(String email) {
       return this.userService.findByEmail(email)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    private void checkOwnership(UserEntity user, ProjectEntity project) {
        if (!project.isOwner(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }
}