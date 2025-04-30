package it.riccisi.babilonia.obsolete.api;

import it.riccisi.babilonia.application.service.CompendiumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@RequiredArgsConstructor
public final class CompendiumController {

    private CompendiumService compendiumService;

    @GetMapping("/{id}/progress")
    public ResponseEntity<Double> getCompendiumProgress(
        @PathVariable String id,
        @RequestParam(defaultValue = "en") String lang
    ) {
        return ResponseEntity.ok(this.compendiumService.computeProgress(id, lang));
    }
}