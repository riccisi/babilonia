package it.riccisi.babilonia.obsolete.api;

import it.riccisi.babilonia.application.service.DefaultMappingService;
import it.riccisi.babilonia.domain.service.jpa.entity.DefaultMapping;
import it.riccisi.babilonia.domain.ItemType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/mappings/default")
@RequiredArgsConstructor
public class DefaultMappingController {

    private final DefaultMappingService defaultMappingService;

    @GetMapping
    public List<Map<String, Object>> getAllMappings() {
        return this.defaultMappingService.getAll().stream()
            .map(DefaultMapping::toView)
            .toList();
    }

    @GetMapping("/{itemType}")
    public Map<String, Object> getMappingByItemType(@PathVariable final ItemType itemType) {
        return this.defaultMappingService.getByItemType(itemType)
            .map(DefaultMapping::toView)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    // visibile solo ad admin
    @PostMapping("/sync")
    @PreAuthorize("hasRole('ADMIN')")
    public void syncFromBabele(@RequestBody final Map<ItemType, Map<String, String>> newMappings) {
        this.defaultMappingService.syncFromBabele(newMappings);
    }
}