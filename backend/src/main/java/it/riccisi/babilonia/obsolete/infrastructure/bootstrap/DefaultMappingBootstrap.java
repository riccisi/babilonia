package it.riccisi.babilonia.obsolete.infrastructure.bootstrap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.riccisi.babilonia.domain.service.jpa.entity.DefaultMapping;
import it.riccisi.babilonia.domain.ItemType;
import it.riccisi.babilonia.domain.repository.DefaultMappingRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class DefaultMappingBootstrap {

    private final DefaultMappingRepository repository;

    @PostConstruct
    public void loadIfEmpty() {
        if (!this.repository.findAll().isEmpty()) {
            return; // non sovrascrivere
        }

        final ObjectMapper mapper = new ObjectMapper();

        try (InputStream input = getClass().getResourceAsStream("/mappings/default-mappings.json")) {
            TypeReference<Map<ItemType, Map<String, String>>> type = new TypeReference<>() {};
            Map<ItemType, Map<String, String>> mappings = mapper.readValue(input, type);

            final LocalDateTime now = LocalDateTime.now();

            List<DefaultMapping> defaults = mappings.entrySet().stream()
                .map(e -> new DefaultMapping(e.getKey(), e.getValue(), now))
                .toList();

            this.repository.saveAll(defaults);

        } catch (IOException e) {
            throw new IllegalStateException("Impossibile caricare defaultMappings.json", e);
        }
    }
}
