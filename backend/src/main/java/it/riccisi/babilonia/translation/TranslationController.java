package it.riccisi.babilonia.translation;

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

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/projects/{projectId}/translations")
@RequiredArgsConstructor
public class TranslationController {

    private final TranslationService translationService;
    private final UserDataService userService;
    private final ProjectService projectService;

    @GetMapping
    public ResponseEntity<List<TranslationFile>> list(@PathVariable UUID projectId,
                                                      @RequestParam String language,
                                                      @AuthenticationPrincipal Jwt jwt) {

        final UserData user = userService.syncFromJwt(jwt);

        final Project project = projectService.findById(projectId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!project.hasAccess(user)) throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        return ResponseEntity.ok(this.translationService.list(project, language));
    }

    @PostMapping
    public ResponseEntity<TranslationFile> upload(@PathVariable UUID projectId,
                                                  @RequestBody TranslationFile input,
                                                  @AuthenticationPrincipal Jwt jwt) {

        final UserData user = this.userService.syncFromJwt(jwt);

        final Project project = projectService.findById(projectId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!(project.isOwner(user) || project.isCollaborator(user)))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        input.setProject(project);
        input.setCreatedAt(Instant.now());
        input.setUpdatedAt(Instant.now());

        return ResponseEntity.status(HttpStatus.CREATED).body(this.translationService.save(input));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TranslationFile> get(@PathVariable UUID projectId,
                                               @PathVariable UUID id,
                                               @AuthenticationPrincipal Jwt jwt) {

        final UserData user = this.userService.syncFromJwt(jwt);
        final Project project = this.projectService.findById(projectId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!project.hasAccess(user)) throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        return this.translationService.get(id)
            .map(ResponseEntity::ok)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}