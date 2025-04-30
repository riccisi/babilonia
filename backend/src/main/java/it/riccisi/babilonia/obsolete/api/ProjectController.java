package it.riccisi.babilonia.obsolete.api;

import it.riccisi.babilonia.domain.*;
import it.riccisi.babilonia.infrastructure.security.CurrentUser;
import it.riccisi.babilonia.runnable.ImportCompendiumTask;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    @NonNull private final Projects projects;
    @NonNull private final FoundrySessions sessions;

    private final ExecutorService executor = Executors.newCachedThreadPool();


    /**
     * Creates a new project and assigns the current user as its owner.
     *
     * @param user        the current user creating the project
     * @param name        the name of the project to be created
     * @param description a brief description of the project
     * @param instanceId  the unique identifier of the Foundry instance associated with the project
     * @param system      the game system on which the project will operate
     * @param world       the world associated with the project
     * @return a {@code ResponseEntity} with the project ID as its body and an HTTP status of 202 (Accepted)
     * @throws Throwable if an error occurs during the project creation process
     */
    @PostMapping
    public ResponseEntity<String> create(
        @CurrentUser User user,
        @RequestParam String name,
        @RequestParam String description,
        @RequestParam String instanceId,
        @RequestParam String system,
        @RequestParam String world
    ) throws Throwable {

        final Project project = this.projects.create(name, description, instanceId, system, world);
        project.addMember(user, ProjectRole.OWNER);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(project.id());
    }

    /**
     * Imports a list of compendium keys into the specified project asynchronously.
     *
     * @param user the current authenticated user
     * @param projectId the ID of the project into which the compendiums are being imported
     * @param keys a list of compendium keys to be imported
     * @return a {@code ResponseEntity} containing the project ID and an HTTP status of 202 (Accepted)
     * @throws ResponseStatusException if the project is not found, the user lacks the necessary permissions, or the Foundry session cannot be retrieved
     */
    @PostMapping
    public ResponseEntity<String> importCompendium(
        @CurrentUser User user,
        @RequestParam String projectId,
        @RequestBody List<String> keys
    ) {

        final Project project = this.projects.getById(projectId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(!user.asMemberOf(project).canManageCompendium()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        final FoundrySession session = this.sessions.getByInstanceId(project.instanceId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        this.executor.submit(() -> {
            try {

                int index = 0;
                int total = keys.size();
                project.setStatus(ProjectStatus.READY);

                for (String key : keys) {
                    final String json = session.fetchCompendiumData(key).get();
                    project.addCompendium(key, );
                    project.updateProgress((index + 1.0) / total);
                    project.save();
                }

                project.setStatus(ProjectStatus.READY);
                project.updateProgress(1.0);
                project.save();

            } catch (Exception e) {
                project.setStatus(ProjectStatus.FAILED);
                project.save();
                throw new RuntimeException("Errore durante l'import asincrono", e);
            }
        });

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(project.id());
    }

    /**
     * Returns the status and progress of project creation.
     */
    @GetMapping("/{id}/status")
    public ResponseEntity<ProjectStatusResponse> getStatus(@PathVariable String id) {
        final Project project = this.projects.getById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return ResponseEntity.ok(new ProjectStatusResponse(project.status(), project.progress()));
    }

    /**
     * Returns the complete project only if the status is READY.
     */
//    @GetMapping("/{id}")
    /*public ResponseEntity<Project> get(@PathVariable String id) {
        final ProjectStatus status = this.projectService.getStatus(id);
        if (status != ProjectStatus.READY) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // o 409 Conflict
        }

        final Project project = this.projectService.get(id);
        return ResponseEntity.ok(project);
    }*/

    /*public Iterable<Project> ownedProjects() {
        return () -> projectRepo.findByOwnerId(id)
            .stream()
            .map(ProjectEntity::toModel)
            .toList()
            .iterator();
    }*/


    /*@GetMapping
    public ResponseEntity<List<Project>> list(@AuthenticationPrincipal Jwt jwt) {
        final UserEntity user = userService.syncFromJwt(jwt);
        final List<Project> projects = projectService.listByUser(user);
        return ResponseEntity.ok(projects);
    }*/


    /*@GetMapping("/{id}/progress")
    public ResponseEntity<Double> progress(
        @PathVariable String id,
        @RequestParam String lang
    ) {
        final double value = projectService.translationProgress(id, lang);
        return ResponseEntity.ok(value);
    }*/

    public record ProjectStatusResponse(ProjectStatus status, double progress) {}

    /*@GetMapping
    public ResponseEntity<List<JpaProject>> listMyProjects(@AuthenticationPrincipal Jwt jwt) {
        UserData user = this.userService.syncFromJwt(jwt);
        return ResponseEntity.ok(projectService.getProjectsForUser(user));
    }

    @PostMapping
    public ResponseEntity<JpaProject> createProject(@AuthenticationPrincipal Jwt jwt, @RequestBody JpaProject project) {
        UserData user = userService.syncFromJwt(jwt);
        project.setCreatedAt(Instant.now());
        JpaProject saved = projectService.save(project);
        this.projectService.addMember(saved, user, ProjectRole.OWNER);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JpaProject> updateProject(
        @PathVariable String id,
        @AuthenticationPrincipal Jwt jwt,
        @RequestBody JpaProject updated) {
        final UserData user = this.userService.syncFromJwt(jwt);

        final JpaProject existing = this.projectService.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!existing.isOwner(user)) throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        existing.updateFrom(updated);

        return ResponseEntity.ok(this.projectService.save(existing));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable String id, @AuthenticationPrincipal Jwt jwt) {
        final UserData user = this.userService.syncFromJwt(jwt);

        final JpaProject existing = this.projectService.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!existing.isOwner(user)) throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        this.projectService.delete(existing);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/progress")
    public ResponseEntity<Double> getProjectProgress(
        @PathVariable String id,
        @RequestParam(defaultValue = "en") String lang
    ) {
        return ResponseEntity.ok(this.projectService.computeProgress(id, lang));
    }*/
}