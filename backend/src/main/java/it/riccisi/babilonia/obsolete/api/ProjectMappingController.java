package it.riccisi.babilonia.obsolete.api;

import it.riccisi.babilonia.application.service.ProjectMappingService;
import it.riccisi.babilonia.domain.ItemType;
import it.riccisi.babilonia.domain.service.jpa.entity.ProjectMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/projects/{projectId}/mappings")
@RequiredArgsConstructor
public final class ProjectMappingController {

    private final ProjectMappingService service;

    @GetMapping
    public List<Map<String, Object>> getAll(@PathVariable String projectId) {
        return service.getAll(projectId).stream()
            .map(ProjectMapping::toView)
            .toList();
    }

    @GetMapping("/{itemType}")
    public Map<String, Object> get(@PathVariable String projectId, @PathVariable ItemType itemType) {
        return service.get(projectId, itemType)
            .map(ProjectMapping::toView)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{itemType}")
    public Map<String, Object> update(
        @PathVariable String projectId,
        @PathVariable ItemType itemType,
        @RequestBody Map<String, String> fields
    ) {
        return this.service.update(projectId, itemType, fields).toView();
    }

    @DeleteMapping("/{itemType}")
    public void delete(@PathVariable String projectId, @PathVariable ItemType itemType) {
        this.service.delete(projectId, itemType);
    }
}