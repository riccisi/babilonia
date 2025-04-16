package it.riccisi.babilonia.project.membership;

import it.riccisi.babilonia.project.Project;
import it.riccisi.babilonia.project.ProjectRepository;
import it.riccisi.babilonia.project.ProjectService;
import it.riccisi.babilonia.user.UserData;
import it.riccisi.babilonia.user.UserDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/projects/{projectId}/members")
@RequiredArgsConstructor
public class ProjectMembershipController {

    private final ProjectRepository projectRepository;
    private final ProjectMembershipRepository membershipRepository;
    private final ProjectService projectService;
    private final UserDataService userService;

    @GetMapping
    public ResponseEntity<List<ProjectMembership>> listMembers(@PathVariable UUID projectId, @AuthenticationPrincipal Jwt jwt) {

        final Project project = getProject(projectId);
        final UserData currentUser = this.userService.syncFromJwt(jwt);
        this.checkOwnership(currentUser, project);

        return ResponseEntity.ok(this.membershipRepository.findByProject(project));
    }

    @PostMapping
    public ResponseEntity<Void> addMember(
        @AuthenticationPrincipal Jwt jwt,
        @PathVariable UUID projectId,
        @RequestParam String userEmail,
        @RequestParam ProjectRole role) {

        final Project project = getProject(projectId);
        final UserData currentUser = this.userService.syncFromJwt(jwt);
        this.checkOwnership(currentUser, project);

        final UserData user = this.getUser(userEmail);
        this.projectService.addMember(project, user, role);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> removeMember(
        @AuthenticationPrincipal Jwt jwt,
        @PathVariable UUID projectId,
        @RequestParam String userEmail) {

        final Project project = this.getProject(projectId);
        final UserData currentUser = this.userService.syncFromJwt(jwt);
        this.checkOwnership(currentUser, project);

        final UserData user = this.getUser(userEmail);
        this.projectService.removeMember(project, user);

        return ResponseEntity.noContent().build();
    }

    private Project getProject(UUID projectId) {
        return this.projectRepository.findById(projectId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    private UserData getUser(String email) {
       return this.userService.findByEmail(email)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    private void checkOwnership(UserData user, Project project) {
        if (!project.isOwner(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }
}