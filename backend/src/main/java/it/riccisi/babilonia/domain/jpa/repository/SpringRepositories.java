package it.riccisi.babilonia.domain.jpa.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public final class SpringRepositories implements Repositories {
    private final CompendiumRepository compendiumRepository;
    private final TranslationRepository translationRepository;
    private final CompendiumMappingsRepository compendiumMappingsRepository;
    private final DefaultMappingsRepository defaultMappingsRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectMembershipRepository projectMembershipRepository;
    private final FoundryConnectionRepository foundryConnectionRepository;
    private final ProjectMappingsRepository projectMappingsRepository;

    @Override
    public CompendiumRepository compendiums() {
        return this.compendiumRepository;
    }

    @Override
    public TranslationRepository translations() {
        return this.translationRepository;
    }

    @Override
    public CompendiumMappingsRepository compendiumMappings() {
        return this.compendiumMappingsRepository;
    }

    @Override
    public DefaultMappingsRepository defaultMappings() {
        return this.defaultMappingsRepository;
    }

    @Override
    public ProjectRepository projects() {
        return this.projectRepository;
    }

    @Override
    public UserRepository users() {
        return this.userRepository;
    }

    @Override
    public ProjectMembershipRepository projectMemberships() {
        return this.projectMembershipRepository;
    }

    @Override
    public ProjectMappingsRepository projectMappings() {
        return this.projectMappingsRepository;
    }

    @Override
    public FoundryConnectionRepository foundryConnections() {
        return this.foundryConnectionRepository;
    }
}
