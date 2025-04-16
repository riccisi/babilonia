package it.riccisi.babilonia.project;

import it.riccisi.babilonia.project.membership.ProjectMembership;
import it.riccisi.babilonia.project.membership.ProjectRole;
import it.riccisi.babilonia.user.UserData;
import it.riccisi.babilonia.user.UserDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final UserDataService userService;

    @GetMapping
    public ResponseEntity<List<Project>> listMyProjects(@AuthenticationPrincipal Jwt jwt) {
        UserData user = this.userService.syncFromJwt(jwt);
        return ResponseEntity.ok(projectService.getProjectsForUser(user));
    }

    @PostMapping
    public ResponseEntity<Project> createProject(@AuthenticationPrincipal Jwt jwt, @RequestBody Project project) {
        UserData user = userService.syncFromJwt(jwt);
        project.setCreatedAt(Instant.now());
        Project saved = projectService.save(project);
        this.projectService.addMember(saved, user, ProjectRole.OWNER);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable UUID id, @AuthenticationPrincipal Jwt jwt, @RequestBody Project updated) {
        final UserData user = this.userService.syncFromJwt(jwt);

        final Project existing = this.projectService.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!existing.isOwner(user)) throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        existing.updateFrom(updated);

        return ResponseEntity.ok(this.projectService.save(existing));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable UUID id, @AuthenticationPrincipal Jwt jwt) {
        final UserData user = this.userService.syncFromJwt(jwt);

        final Project existing = this.projectService.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!existing.isOwner(user)) throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        this.projectService.delete(existing);

        return ResponseEntity.noContent().build();
    }
}