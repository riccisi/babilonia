package it.riccisi.babilonia.api;

import it.riccisi.babilonia.domain.*;
import it.riccisi.babilonia.infrastructure.security.CurrentUser;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The CompendiumController class handles HTTP requests for managing compendiums
 * within a project. It provides endpoints for importing compendium data asynchronously
 * and retrieving the list of compendiums associated with a project.
 */
@RestController
@RequestMapping("/api/projects/{projectId}/compendiums")
@RequiredArgsConstructor
public class CompendiumController {

    private final ExecutorService executor = Executors.newCachedThreadPool();

    @NonNull private final Projects projects;
    @NonNull private final FoundrySessions sessions;

    /**
     * Initiates an asynchronous process to import compendium data for a specific project.
     *
     * @param user the current user making the request, injected from the security context
     * @param projectId the unique identifier of the project where the compendium data will be imported
     * @param keys the list of keys representing the compendium data to be fetched and imported
     * @return a ResponseEntity containing the project ID of the associated project and an HTTP status of ACCEPTED
     * @throws ResponseStatusException if the project ID is not found, the user does not have permission to manage
     *                                 the compendium, or the project instance cannot be resolved
     */
    @PostMapping
    public ResponseEntity<String> importCompendium(
        @CurrentUser User user,
        @PathVariable String projectId,
        @RequestBody List<String> keys) {

        final Project project = this.projects.getById(projectId);
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
                    final CompendiumData data = session.fetchCompendiumData(key).get();
                    project.addCompendium(data);
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
     * Retrieves a list of compendiums associated with the specified project.
     *
     * @param me the current authenticated user making the request
     * @param projectId the unique identifier of the project for which compendiums are being requested
     * @return a ResponseEntity containing an iterable collection of compendiums and an HTTP status of OK
     * @throws ResponseStatusException if the project ID is not found or if the current user does not have read permissions for the project
     */
    @GetMapping
    public ResponseEntity<Iterable<Compendium>> list(
        @CurrentUser User me,
        @PathVariable String projectId
    ) {
        final Project project = this.projects.getById(projectId);
        if(!me.asMemberOf(project).canRead()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        Iterable<Compendium> compendiums = project.compendiums();
        return ResponseEntity.ok(compendiums);
    }
}